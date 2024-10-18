package cn.sichu.system.service;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.Role;
import cn.sichu.system.model.form.RoleForm;
import cn.sichu.system.model.query.RolePageQuery;
import cn.sichu.system.model.vo.RolePageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 角色业务接口层
 *
 * @author sichu huang
 * @since 2024/10/17 00:01:04
 */
public interface RoleService extends IService<Role> {

    /**
     * 角色分页列表
     *
     * @param queryParams queryParams
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.RolePageVO>
     * @author sichu huang
     * @since 2024/10/17 00:02:00
     */
    Page<RolePageVO> getRolePage(RolePageQuery queryParams);

    /**
     * 角色下拉列表
     *
     * @return java.util.List<cn.sichu.model.Option < java.lang.Long>>
     * @author sichu huang
     * @since 2024/10/17 00:02:10
     */
    List<Option<Long>> listRoleOptions();

    /**
     * @param roleForm roleForm
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:02:18
     */
    boolean saveRole(RoleForm roleForm);

    /**
     * 获取角色表单数据
     *
     * @param roleId 角色ID
     * @return cn.sichu.model.form.RoleForm 角色表单数据
     * @author sichu huang
     * @since 2024/10/17 00:02:28
     */
    RoleForm getRoleForm(Long roleId);

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1:启用；0:禁用)
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:02:51
     */
    boolean updateRoleStatus(Long roleId, Integer status);

    /**
     * 批量删除角色
     *
     * @param ids 角色ID，多个使用英文逗号(,)分割
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:03:03
     */
    boolean deleteRoles(String ids);

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long> 菜单ID集合(包括按钮权限ID)
     * @author sichu huang
     * @since 2024/10/17 00:03:14
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * 修改角色的资源权限
     *
     * @param roleId  roleId
     * @param menuIds menuIds
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:03:26
     */
    boolean assignMenusToRole(Long roleId, List<Long> menuIds);

    /**
     * 获取最大范围的数据权限
     *
     * @param roles roles
     * @return java.lang.Integer
     * @author sichu huang
     * @since 2024/10/17 00:03:37
     */
    Integer getMaximumDataScope(Set<String> roles);

}
