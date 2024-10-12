package cn.sichu.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.sichu.auth.model.query.OnlineUserQuery;
import cn.sichu.auth.service.IOnlineUserService;
import cn.sichu.constant.CacheConstants;
import cn.sichu.constant.ContainerConstants;
import cn.sichu.constant.SysConstants;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.mp.service.impl.BaseServiceImpl;
import cn.sichu.enums.DataScopeEnum;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.model.dto.RoleDTO;
import cn.sichu.system.mapper.RoleMapper;
import cn.sichu.system.model.entity.RoleDO;
import cn.sichu.system.model.query.RoleQuery;
import cn.sichu.system.model.req.RoleReq;
import cn.sichu.system.model.resp.MenuResp;
import cn.sichu.system.model.resp.RoleDetailResp;
import cn.sichu.system.model.resp.RoleResp;
import cn.sichu.system.service.*;
import cn.sichu.utils.helper.LoginHelper;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class RoleServiceImpl
    extends BaseServiceImpl<RoleMapper, RoleDO, RoleResp, RoleDetailResp, RoleQuery, RoleReq>
    implements IRoleService {

    private final IMenuService menuService;
    private final IRoleMenuService roleMenuService;
    private final IRoleDeptService roleDeptService;
    private final IUserRoleService userRoleService;
    private final IOnlineUserService onlineUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(RoleReq req) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, null), "新增失败，[{}] 已存在", name);
        String code = req.getCode();
        CheckUtils.throwIf(this.isCodeExists(code, null), "新增失败，[{}] 已存在", code);
        // 新增信息
        Long roleId = super.add(req);
        // 保存角色和菜单关联
        roleMenuService.add(req.getMenuIds(), roleId);
        // 保存角色和部门关联
        roleDeptService.add(req.getDeptIds(), roleId);
        return roleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(key = "#req.code == 'admin' ? 'ALL' : #req.code",
        name = CacheConstants.MENU_KEY_PREFIX)
    public void update(RoleReq req, Long id) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, id), "修改失败，[{}] 已存在", name);
        RoleDO oldRole = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getCode(), oldRole.getCode(), "角色编码不允许修改",
            oldRole.getName());
        DataScopeEnum oldDataScope = oldRole.getDataScope();
        if (Boolean.TRUE.equals(oldRole.getIsSystem())) {
            CheckUtils.throwIfNotEqual(req.getDataScope(), oldDataScope,
                "[{}] 是系统内置角色，不允许修改角色数据权限", oldRole.getName());
        }
        // 更新信息
        super.update(req, id);
        if (SysConstants.ADMIN_ROLE_CODE.equals(req.getCode())) {
            return;
        }
        // 保存角色和菜单关联
        boolean isSaveMenuSuccess = roleMenuService.add(req.getMenuIds(), id);
        // 保存角色和部门关联
        boolean isSaveDeptSuccess = roleDeptService.add(req.getDeptIds(), id);
        // 如果功能权限或数据权限有变更，则更新在线用户权限信息
        if (ObjectUtil.notEqual(req.getDataScope(), oldDataScope) || isSaveMenuSuccess
            || isSaveDeptSuccess) {
            OnlineUserQuery query = new OnlineUserQuery();
            query.setRoleId(id);
            List<LoginUser> loginUserList = onlineUserService.list(query);
            loginUserList.parallelStream().forEach(loginUser -> {
                loginUser.setRoles(this.listByUserId(loginUser.getId()));
                loginUser.setPermissions(this.listPermissionByUserId(loginUser.getId()));
                LoginHelper.updateLoginUser(loginUser, loginUser.getToken());
            });
        }
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<RoleDO> list = baseMapper.lambdaQuery().select(RoleDO::getName, RoleDO::getIsSystem)
            .in(RoleDO::getId, ids).list();
        Optional<RoleDO> isSystemData = list.stream().filter(RoleDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选角色 [{}] 是系统内置角色，不允许删除",
            isSystemData.orElseGet(RoleDO::new).getName());
        CheckUtils.throwIf(userRoleService.isRoleIdExists(ids),
            "所选角色存在用户关联，请解除关联后重试");
        // 删除角色和菜单关联
        roleMenuService.deleteByRoleIds(ids);
        // 删除角色和部门关联
        roleDeptService.deleteByRoleIds(ids);
    }

    @Override
    protected void fill(Object obj) {
        super.fill(obj);
        if (obj instanceof RoleDetailResp detail) {
            Long roleId = detail.getId();
            if (SysConstants.ADMIN_ROLE_CODE.equals(detail.getCode())) {
                List<MenuResp> list = menuService.listAll();
                List<Long> menuIds = list.stream().map(MenuResp::getId).toList();
                detail.setMenuIds(menuIds);
            } else {
                detail.setMenuIds(
                    roleMenuService.listMenuIdByRoleIds(CollUtil.newArrayList(roleId)));
            }
        }
    }

    @Override
    public Set<String> listPermissionByUserId(Long userId) {
        Set<String> roleCodeSet = this.listCodeByUserId(userId);
        // 超级管理员赋予全部权限
        if (roleCodeSet.contains(SysConstants.ADMIN_ROLE_CODE)) {
            return CollUtil.newHashSet(SysConstants.ALL_PERMISSION);
        }
        return menuService.listPermissionByUserId(userId);
    }

    @Override
    @ContainerMethod(namespace = ContainerConstants.USER_ROLE_NAME_LIST,
        type = MappingType.ORDER_OF_KEYS)
    public List<String> listNameByIds(List<Long> ids) {
        List<RoleDO> roleList =
            baseMapper.lambdaQuery().select(RoleDO::getName).in(RoleDO::getId, ids).list();
        return roleList.stream().map(RoleDO::getName).toList();
    }

    @Override
    public Set<String> listCodeByUserId(Long userId) {
        List<Long> roleIdList = userRoleService.listRoleIdByUserId(userId);
        List<RoleDO> roleList =
            baseMapper.lambdaQuery().select(RoleDO::getCode).in(RoleDO::getId, roleIdList).list();
        return roleList.stream().map(RoleDO::getCode).collect(Collectors.toSet());
    }

    @Override
    public Set<RoleDTO> listByUserId(Long userId) {
        List<Long> roleIdList = userRoleService.listRoleIdByUserId(userId);
        List<RoleDO> roleList = baseMapper.lambdaQuery().in(RoleDO::getId, roleIdList).list();
        return new HashSet<>(BeanUtil.copyToList(roleList, RoleDTO.class));
    }

    @Override
    public RoleDO getByCode(String code) {
        return baseMapper.lambdaQuery().eq(RoleDO::getCode, code).one();
    }

    @Override
    public List<RoleDO> listByNames(List<String> list) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<RoleDO>lambdaQuery().in(RoleDO::getName, list));
    }

    @Override
    public int countByNames(List<String> roleNames) {
        if (CollUtil.isEmpty(roleNames)) {
            return 0;
        }
        return (int)this.count(Wrappers.<RoleDO>lambdaQuery().in(RoleDO::getName, roleNames));
    }

    /**
     * 名称是否存在
     *
     * @param name 名称
     * @param id   ID
     * @return boolean
     * @author sichu huang
     * @date 2024/10/11
     **/
    private boolean isNameExists(String name, Long id) {
        return baseMapper.lambdaQuery().eq(RoleDO::getName, name).ne(null != id, RoleDO::getId, id)
            .exists();
    }

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @param id   ID
     * @return boolean
     * @author sichu huang
     * @date 2024/10/11
     **/
    private boolean isCodeExists(String code, Long id) {
        return baseMapper.lambdaQuery().eq(RoleDO::getCode, code).ne(null != id, RoleDO::getId, id)
            .exists();
    }
}
