package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.MenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface MenuMapper extends BaseMapper<MenuDO> {
    /**
     * 根据用户ID 查询权限码
     *
     * @param userId 用户ID
     * @return java.util.Set<java.lang.String> 权限码集合
     * @author sichu huang
     * @date 2024/10/11
     **/
    Set<String> selectPermissionByUserId(@Param("userId") Long userId);

    /**
     * 根据角色编码查询
     *
     * @param roleCode 角色编码
     * @return java.util.List<cn.sichu.system.model.entity.MenuDO> 菜单列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<MenuDO> selectListByRoleCode(@Param("roleCode") String roleCode);
}
