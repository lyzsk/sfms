package cn.sichu.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * 图形验证码过期时间
     **/
    // @Value("${sfms.captcha.graphic.expirationInMinutes}")
    // private long expirationInMinutes;

    /**
     * 邮箱验证码配置
     **/
    private CaptchaMail mail;

    /**
     * 短信验证码配置
     **/
    private CaptchaSms sms;

    /**
     * 邮箱验证码配置
     **/
    @Data
    public static class CaptchaMail {
        /**
         * 内容长度
         **/
        private int length;

        /**
         * 过期时间
         **/
        private long expirationInMinutes;

        /**
         * 模板路径
         **/
        private String templatePath;
    }

    /**
     * 短信验证码配置
     **/
    @Data
    public static class CaptchaSms {
        /**
         * 内容长度
         **/
        private int length;

        /**
         * 过期时间
         **/
        private long expirationInMinutes;

        /**
         * 模板 ID
         **/
        private String templateId;
    }
}
