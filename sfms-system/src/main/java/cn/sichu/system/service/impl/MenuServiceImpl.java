package cn.sichu.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.mp.service.impl.BaseServiceImpl;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.system.enums.MenuTypeEnum;
import cn.sichu.system.mapper.MenuMapper;
import cn.sichu.system.model.entity.MenuDO;
import cn.sichu.system.model.query.MenuQuery;
import cn.sichu.system.model.req.MenuReq;
import cn.sichu.system.model.resp.MenuResp;
import cn.sichu.system.service.IMenuService;
import com.alicp.jetcache.anno.Cached;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class MenuServiceImpl
    extends BaseServiceImpl<MenuMapper, MenuDO, MenuResp, MenuResp, MenuQuery, MenuReq>
    implements IMenuService {

    @Override
    public Long add(MenuReq req) {
        String title = req.getTitle();
        CheckUtils.throwIf(this.isTitleExists(title, req.getParentId(), null),
            "新增失败，标题 [{}] 已存在", title);
        // 目录和菜单的组件名称不能重复
        if (!MenuTypeEnum.BUTTON.equals(req.getType())) {
            String name = req.getName();
            CheckUtils.throwIf(this.isNameExists(name, null), "新增失败，组件名称 [{}] 已存在",
                name);
        }
        // 目录类型菜单，默认为 Layout
        if (MenuTypeEnum.DIR.equals(req.getType())) {
            req.setComponent(StrUtil.blankToDefault(req.getComponent(), "Layout"));
        }
        RedisUtils.deleteByPattern(CacheConstants.MENU_KEY_PREFIX + StringConstants.ASTERISK);
        return super.add(req);
    }

    @Override
    public void update(MenuReq req, Long id) {
        String title = req.getTitle();
        CheckUtils.throwIf(this.isTitleExists(title, req.getParentId(), id),
            "修改失败，标题 [{}] 已存在", title);
        // 目录和菜单的组件名称不能重复
        if (!MenuTypeEnum.BUTTON.equals(req.getType())) {
            String name = req.getName();
            CheckUtils.throwIf(this.isNameExists(name, id), "修改失败，组件名称 [{}] 已存在", name);
        }
        MenuDO oldMenu = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getType(), oldMenu.getType(), "不允许修改菜单类型");
        super.update(req, id);
        RedisUtils.deleteByPattern(CacheConstants.MENU_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        baseMapper.lambdaUpdate().in(MenuDO::getParentId, ids).remove();
        super.delete(ids);
        RedisUtils.deleteByPattern(CacheConstants.MENU_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    @Cached(key = "'ALL'", name = CacheConstants.MENU_KEY_PREFIX)
    public List<MenuResp> listAll() {
        return super.list(new MenuQuery(DisEnableStatusEnum.ENABLE), null);
    }

    @Override
    public Set<String> listPermissionByUserId(Long userId) {
        return baseMapper.selectPermissionByUserId(userId);
    }

    @Override
    @Cached(key = "#roleCode", name = CacheConstants.MENU_KEY_PREFIX)
    public List<MenuResp> listByRoleCode(String roleCode) {
        List<MenuDO> menuList = baseMapper.selectListByRoleCode(roleCode);
        List<MenuResp> list = BeanUtil.copyToList(menuList, MenuResp.class);
        list.forEach(this::fill);
        return list;
    }

    /**
     * 标题是否存在
     *
     * @param title    标题
     * @param parentId 上级ID
     * @param id       ID
     * @return boolean true：存在；false：不存在
     * @author sichu huang
     * @date 2024/10/11
     **/
    private boolean isTitleExists(String title, Long parentId, Long id) {
        return baseMapper.lambdaQuery().eq(MenuDO::getTitle, title)
            .eq(MenuDO::getParentId, parentId).ne(null != id, MenuDO::getId, id).exists();
    }

    /**
     * 名称是否存在
     *
     * @param name 标题
     * @param id   ID
     * @return boolean true：存在；false：不存在
     * @author sichu huang
     * @date 2024/10/11
     **/
    private boolean isNameExists(String name, Long id) {
        return baseMapper.lambdaQuery().eq(MenuDO::getName, name)
            .ne(MenuDO::getType, MenuTypeEnum.BUTTON).ne(null != id, MenuDO::getId, id).exists();
    }
}
