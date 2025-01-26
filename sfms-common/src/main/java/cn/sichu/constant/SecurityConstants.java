package cn.sichu.constant;

/**
 * 缓存常量
 *
 * @author sichu huang
 * @since 2024/10/16 22:17
 */
public interface SecurityConstants {

    /**
     * 角色和权限缓存前缀
     */
    String ROLE_PERMS_PREFIX = "role_perms:";

    /**
     * 黑名单Token缓存前缀
     */
    String BLACKLIST_TOKEN_PREFIX = "token:blacklist:";

    /**
     * 登录路径
     */
    String LOGIN_PATH = "/api/v1/auth/login";

    /**
     * JWT Token 前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";
}
