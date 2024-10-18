package cn.sichu.system.mapper;

import cn.sichu.system.model.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sichu huang
 * @since 2024/10/16 23:27
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取角色绑定的用户数
     *
     * @param roleId 角色ID
     * @return int
     * @author sichu huang
     * @since 2024/10/16 23:27:35
     */
    int countUsersForRole(Long roleId);
}
