package cn.sichu.model.dto;

import cn.hutool.core.collection.CollUtil;
import cn.sichu.constant.SysConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    private Long id;

    /**
     * 用户名
     **/
    private String username;

    /**
     * 部门 ID
     **/
    private Long deptId;

    /**
     * 权限码集合
     **/
    private Set<String> permissions;

    /**
     * 角色编码集合
     **/
    private Set<String> roleCodes;

    /**
     * 角色集合
     **/
    private Set<RoleDTO> roles;

    /**
     * 令牌
     **/
    private String token;

    /**
     * IP
     **/
    private String ip;

    /**
     * IP 归属地
     **/
    private String address;

    /**
     * 浏览器
     **/
    private String browser;

    /**
     * 操作系统
     **/
    private String os;

    /**
     * 登录时间
     **/
    private Date loginTime;

    /**
     * 最后一次修改密码时间
     **/
    private Date pwdResetTime;

    /**
     * 登录时系统设置的密码过期天数
     **/
    private Integer passwordExpirationDays;

    public LoginUser(Set<String> permissions, Set<RoleDTO> roles, Integer passwordExpirationDays) {
        this.permissions = permissions;
        this.setRoles(roles);
        this.passwordExpirationDays = passwordExpirationDays;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
        this.roleCodes = roles.stream().map(RoleDTO::getCode).collect(Collectors.toSet());
    }

    /**
     * 是否为管理员
     *
     * @return boolean true：是；false：否
     * @author sichu huang
     * @date 2024/10/12
     **/
    public boolean isAdmin() {
        if (CollUtil.isEmpty(roleCodes)) {
            return false;
        }
        return roleCodes.contains(SysConstants.ADMIN_ROLE_CODE);
    }

    /**
     * 密码是否已过期
     *
     * @return boolean 是否过期
     * @author sichu huang
     * @date 2024/10/12
     **/
    public boolean isPasswordExpired() {
        // 永久有效
        if (this.passwordExpirationDays == null || this.passwordExpirationDays <= SysConstants.NO) {
            return false;
        }
        // 初始密码（第三方登录用户）暂不提示修改
        if (this.pwdResetTime == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.pwdResetTime);
        calendar.add(Calendar.DAY_OF_MONTH, this.passwordExpirationDays.intValue());
        Date expirationDate = calendar.getTime();
        return expirationDate.before(new Date());
    }
}
