package cn.sichu.constant;

import cn.sichu.core.constant.StringConstants;

/**
 * 缓存相关常量
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class CacheConstants {

    /**
     * 分隔符
     **/
    public static final String DELIMITER = StringConstants.COLON;

    /**
     * 登录用户键
     **/
    public static final String LOGIN_USER_KEY = "LOGIN_USER";

    /**
     * 验证码键前缀
     **/
    public static final String CAPTCHA_KEY_PREFIX = "CAPTCHA" + DELIMITER;

    /**
     * 用户缓存键前缀
     **/
    public static final String USER_KEY_PREFIX = "USER" + DELIMITER;
    /**
     * 用户密码错误次数缓存键前缀
     **/
    public static final String USER_PASSWORD_ERROR_KEY_PREFIX =
        USER_KEY_PREFIX + "PASSWORD_ERROR" + DELIMITER;
    /**
     * 菜单缓存键前缀
     **/
    public static final String MENU_KEY_PREFIX = "MENU" + DELIMITER;
    /**
     * 字典缓存键前缀
     **/
    public static final String DICT_KEY_PREFIX = "DICT" + DELIMITER;
    /**
     * 参数缓存键前缀
     **/
    public static final String OPTION_KEY_PREFIX = "OPTION" + DELIMITER;
    /**
     * 仪表盘缓存键前缀
     **/
    public static final String DASHBOARD_KEY_PREFIX = "DASHBOARD" + DELIMITER;
    /**
     * 数据导入临时会话key
     **/
    public static final String DATA_IMPORT_KEY = "SYSTEM" + DELIMITER + "DATA_IMPORT" + DELIMITER;

    private CacheConstants() {
    }
}
