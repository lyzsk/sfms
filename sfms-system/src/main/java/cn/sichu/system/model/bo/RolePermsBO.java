package cn.sichu.system.model.bo;

import lombok.Data;

import java.util.Set;

/**
 * 角色权限业务对象
 *
 * @author sichu huang
 * @since 2024/10/16 22:45
 */
@Data
public class RolePermsBO {
    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 权限标识集合
     */
    private Set<String> perms;
}
