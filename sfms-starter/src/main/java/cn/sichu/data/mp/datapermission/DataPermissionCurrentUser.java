package cn.sichu.data.mp.datapermission;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 当前用户信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class DataPermissionCurrentUser {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色列表
     */
    private Set<CurrentUserRole> roles;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 当前用户角色信息
     */
    public static class CurrentUserRole {

        /**
         * 角色 ID
         */
        private String roleId;

        /**
         * 数据权限
         */
        private DataScope dataScope;

        public CurrentUserRole() {
        }

        public CurrentUserRole(String roleId, DataScope dataScope) {
            this.roleId = roleId;
            this.dataScope = dataScope;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public DataScope getDataScope() {
            return dataScope;
        }

        public void setDataScope(DataScope dataScope) {
            this.dataScope = dataScope;
        }
    }
}
