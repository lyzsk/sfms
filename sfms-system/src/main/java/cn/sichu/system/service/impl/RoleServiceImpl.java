package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.constant.SystemConstants;
import cn.sichu.model.Option;
import cn.sichu.system.converter.RoleConverter;
import cn.sichu.system.core.utils.SecurityUtils;
import cn.sichu.system.mapper.RoleMapper;
import cn.sichu.system.model.entity.Role;
import cn.sichu.system.model.entity.RoleMenu;
import cn.sichu.system.model.form.RoleForm;
import cn.sichu.system.model.query.RolePageQuery;
import cn.sichu.system.model.vo.RolePageVO;
import cn.sichu.system.service.RoleMenuService;
import cn.sichu.system.service.RoleService;
import cn.sichu.system.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author sichu huang
 * @since 2024/10/17 16:19
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final RoleConverter roleConverter;

    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery queryParams) {
        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();
        // 查询数据
        Page<Role> rolePage = this.page(new Page<>(pageNum, pageSize),
            new LambdaQueryWrapper<Role>().and(StrUtil.isNotBlank(keywords),
                    wrapper -> wrapper.like(StrUtil.isNotBlank(keywords), Role::getName, keywords).or()
                        .like(StrUtil.isNotBlank(keywords), Role::getCode, keywords))
                .ne(!SecurityUtils.isRoot(), Role::getCode, SystemConstants.ROOT_ROLE_CODE)
            // 非超级管理员不显示超级管理员角色
        );
        // 实体转换
        return roleConverter.toPageVo(rolePage);
    }

    @Override
    public List<Option<Long>> listRoleOptions() {
        // 查询数据
        List<Role> roleList = this.list(
            new LambdaQueryWrapper<Role>().ne(!SecurityUtils.isRoot(), Role::getCode,
                    SystemConstants.ROOT_ROLE_CODE).select(Role::getId, Role::getName)
                .orderByAsc(Role::getSort));
        // 实体转换
        return roleConverter.entities2Options(roleList);
    }

    @Override
    public boolean saveRole(RoleForm roleForm) {
        Long roleId = roleForm.getId();
        // 编辑角色时，判断角色是否存在
        Role oldRole = null;
        if (roleId != null) {
            oldRole = this.getById(roleId);
            Assert.isTrue(oldRole != null, "角色不存在");
        }
        String roleCode = roleForm.getCode();
        long count = this.count(
            new LambdaQueryWrapper<Role>().ne(roleId != null, Role::getId, roleId).and(
                wrapper -> wrapper.eq(Role::getCode, roleCode).or()
                    .eq(Role::getName, roleForm.getName())));
        Assert.isTrue(count == 0, "角色名称或角色编码已存在，请修改后重试！");
        // 实体转换
        Role role = roleConverter.toEntity(roleForm);
        boolean result = this.saveOrUpdate(role);
        if (result) {
            // 判断角色编码或状态是否修改，修改了则刷新权限缓存
            if (oldRole != null && (!StrUtil.equals(oldRole.getCode(), roleCode)
                || !ObjectUtil.equals(oldRole.getStatus(), roleForm.getStatus()))) {
                roleMenuService.refreshRolePermsCache(oldRole.getCode(), roleCode);
            }
        }
        return result;
    }

    @Override
    public RoleForm getRoleForm(Long roleId) {
        Role entity = this.getById(roleId);
        return roleConverter.toForm(entity);
    }

    @Override
    public boolean updateRoleStatus(Long roleId, Integer status) {
        Role role = this.getById(roleId);
        Assert.isTrue(role != null, "角色不存在");
        assert role != null;
        role.setStatus(status);
        boolean result = this.updateById(role);
        if (result) {
            // 刷新角色的权限缓存
            roleMenuService.refreshRolePermsCache(role.getCode());
        }
        return result;
    }

    @Override
    public boolean deleteRoles(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的角色ID不能为空");
        List<Long> roleIds = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        for (Long roleId : roleIds) {
            Role role = this.getById(roleId);
            Assert.isTrue(role != null, "角色不存在");
            // 判断角色是否被用户关联
            boolean isRoleAssigned = userRoleService.hasAssignedUsers(roleId);
            assert role != null;
            Assert.isTrue(!isRoleAssigned, "角色【{}】已分配用户，请先解除关联后删除", role.getName());
            boolean deleteResult = this.removeById(roleId);
            if (deleteResult) {
                // 删除成功，刷新权限缓存
                roleMenuService.refreshRolePermsCache(role.getCode());
            }
        }
        return true;
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        return roleMenuService.listMenuIdsByRoleId(roleId);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
        Role role = this.getById(roleId);
        Assert.isTrue(role != null, "角色不存在");
        // 删除角色菜单
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        // 新增角色菜单
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<RoleMenu> roleMenus =
                menuIds.stream().map(menuId -> new RoleMenu(roleId, menuId)).toList();
            roleMenuService.saveBatch(roleMenus);
        }
        // 刷新角色的权限缓存
        roleMenuService.refreshRolePermsCache(role.getCode());
        return true;
    }

    @Override
    public Integer getMaximumDataScope(Set<String> roles) {
        return this.baseMapper.getMaximumDataScope(roles);
    }
}
