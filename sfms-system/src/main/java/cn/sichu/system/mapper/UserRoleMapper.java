package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.UserRoleDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface UserRoleMapper extends BaseMapper<UserRoleDo> {
    
    /**
     * 根据用户ID 查询
     *
     * @param userId 用户ID
     * @return java.util.List<java.lang.Long> 角色ID列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    @Select("SELECT role_id FROM t_sys_user_role WHERE user_id = #{userId}")
    List<Long> selectRoleIdByUserId(@Param("userId") Long userId);
}
