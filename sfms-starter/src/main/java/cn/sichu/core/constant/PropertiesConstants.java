package cn.sichu.core.constant;

/**
 * 配置属性相关常量
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class PropertiesConstants {

    /**
     * sfms
     */
    public static final String SFMS = "sfms";

    /**
     * 启用配置
     */
    public static final String ENABLED = "enabled";

    /**
     * 安全配置
     */
    public static final String SECURITY = SFMS + StringConstants.DOT + "security";

    /**
     * 密码编解码配置
     */
    public static final String SECURITY_PASSWORD = SECURITY + StringConstants.DOT + "password";

    /**
     * 加/解密配置
     */
    public static final String SECURITY_CRYPTO = SECURITY + StringConstants.DOT + "crypto";

    /**
     * 限流器配置
     */
    public static final String SECURITY_LIMITER = SECURITY + StringConstants.DOT + "limiter";

    /**
     * Web 配置
     */
    public static final String WEB = SFMS + StringConstants.DOT + "web";

    /**
     * 跨域配置
     */
    public static final String WEB_CORS = WEB + StringConstants.DOT + "cors";

    /**
     * 响应配置
     */
    public static final String WEB_RESPONSE = WEB + StringConstants.DOT + "response";

    /**
     * 链路配置
     */
    public static final String WEB_TRACE = WEB + StringConstants.DOT + "trace";

    /**
     * XSS 配置
     */
    public static final String WEB_XSS = WEB + StringConstants.DOT + "xss";

    /**
     * 日志配置
     */
    public static final String LOG = SFMS + StringConstants.DOT + "log";

    /**
     * 存储配置
     */
    public static final String STORAGE = SFMS + StringConstants.DOT + "storage";

    /**
     * 本地存储配置
     */
    public static final String STORAGE_LOCAL = STORAGE + StringConstants.DOT + "local";

    /**
     * 验证码配置
     */
    public static final String CAPTCHA = SFMS + StringConstants.DOT + "captcha";

    /**
     * 图形验证码配置
     */
    public static final String CAPTCHA_GRAPHIC = CAPTCHA + StringConstants.DOT + "graphic";

    /**
     * 行为验证码配置
     */
    public static final String CAPTCHA_BEHAVIOR = CAPTCHA + StringConstants.DOT + "behavior";

    /**
     * 消息配置
     */
    public static final String MESSAGING = SFMS + StringConstants.DOT + "messaging";

    /**
     * WebSocket 配置
     */
    public static final String MESSAGING_WEBSOCKET = MESSAGING + StringConstants.DOT + "websocket";

    /**
     * 数据权限配置
     */
    public static final String DATA_PERMISSION = SFMS + StringConstants.DOT + "data-permission";

    /**
     * 多租户配置
     */
    public static final String TENANT = SFMS + StringConstants.DOT + "tenant";

    private PropertiesConstants() {
    }
}
