package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.RoleDeptDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface RoleDeptMapper extends BaseMapper<RoleDeptDo> {
    /**
     * 根据角色ID查询
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long> 部门ID列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    @Select("SELECT dept_id FROM t_sys_role_dept WHERE role_id = #{roleId}")
    List<Long> selectDeptIdByRoleId(@Param("roleId") Long roleId);
}
