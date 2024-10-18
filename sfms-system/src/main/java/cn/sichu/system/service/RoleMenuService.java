package cn.sichu.system.service;

import cn.sichu.system.model.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单业务接口
 *
 * @author sichu huang
 * @since 2024/10/16 23:59:50
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long> 菜单ID集合
     * @author sichu huang
     * @since 2024/10/17 00:00:01
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 刷新权限缓存(所有角色)
     *
     * @author sichu huang
     * @since 2024/10/17 00:00:13
     */
    void refreshRolePermsCache();

    /**
     * 刷新权限缓存(指定角色)
     *
     * @param roleCode 角色编码
     * @author sichu huang
     * @since 2024/10/17 00:00:25
     */
    void refreshRolePermsCache(String roleCode);

    /**
     * 刷新权限缓存(修改角色编码时调用)
     *
     * @param oldRoleCode 旧角色编码
     * @param newRoleCode 新角色编码
     * @author sichu huang
     * @since 2024/10/17 00:00:36
     */
    void refreshRolePermsCache(String oldRoleCode, String newRoleCode);

    /**
     * 获取角色权限集合
     *
     * @param roles 角色编码集合
     * @return java.util.Set<java.lang.String> 权限集合
     * @author sichu huang
     * @since 2024/10/17 00:00:48
     */
    Set<String> getRolePermsByRoleCodes(Set<String> roles);
}
