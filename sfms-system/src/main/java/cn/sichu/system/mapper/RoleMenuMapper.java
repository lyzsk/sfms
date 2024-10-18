package cn.sichu.system.mapper;

import cn.sichu.system.model.bo.RolePermsBO;
import cn.sichu.system.model.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author sichu huang
 * @since 2024/10/16 23:23
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long> 菜单ID集合
     * @author sichu huang
     * @since 2024/10/16 23:24:20
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限和拥有权限的角色列表
     *
     * @param roleCode roleCode
     * @return java.util.List<cn.sichu.model.bo.RolePermsBO>
     * @author sichu huang
     * @since 2024/10/16 23:24:32
     */
    List<RolePermsBO> getRolePermsList(String roleCode);

    /**
     * 获取角色权限集合
     *
     * @param roles roles
     * @return java.util.Set<java.lang.String>
     * @author sichu huang
     * @since 2024/10/16 23:24:41
     */
    Set<String> listRolePerms(Set<String> roles);
}
