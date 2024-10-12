package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.RoleMenuDo;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface RoleMenuMapper extends BaseMapper<RoleMenuDo> {
    /**
     * 根据角色ID列表 查询
     *
     * @param roleIds 角色ID列表
     * @return java.util.List<java.lang.Long> 菜单ID列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<Long> selectMenuIdByRoleIds(List<Long> roleIds);
}
