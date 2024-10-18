package cn.sichu.auth.enums;

/**
 * EasyCaptcha 验证码类型枚举
 *
 * @author sichu huang
 * @since 2024/10/17 16:14
 */
public enum CaptchaTypeEnum {
    /**
     * 圆圈干扰验证码
     */
    CIRCLE,
    /**
     * GIF验证码
     */
    GIF,
    /**
     * 干扰线验证码
     */
    LINE,
    /**
     * 扭曲干扰验证码
     */
    SHEAR
}
