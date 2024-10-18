package cn.sichu.system.service;

import cn.sichu.system.model.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    /**
     * 保存用户角色
     *
     * @param userId  userId
     * @param roleIds roleIds
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 15:46:43
     */
    boolean saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * 判断角色是否存在绑定的用户
     *
     * @param roleId 角色ID
     * @return boolean true：已分配 false：未分配
     * @author sichu huang
     * @since 2024/10/17 15:46:54
     */
    boolean hasAssignedUsers(Long roleId);
}
