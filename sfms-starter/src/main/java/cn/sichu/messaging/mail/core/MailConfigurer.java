package cn.sichu.messaging.mail.core;

import cn.sichu.core.utils.validate.ValidationUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface MailConfigurer {

    /**
     * 获取邮件配置
     *
     * @return 邮件配置
     */
    MailConfig getMailConfig();

    /**
     * 应用配置
     *
     * @param mailConfig 邮件配置
     * @param sender     邮件 Sender
     */
    default void apply(MailConfig mailConfig, JavaMailSenderImpl sender) {
        String protocolLowerCase = mailConfig.getProtocol().toLowerCase();
        ValidationUtils.throwIfNotEqual(MailConfig.DEFAULT_PROTOCOL, protocolLowerCase,
            "邮件配置错误：不支持的邮件发送协议: %s".formatted(mailConfig.getProtocol()));
        sender.setProtocol(mailConfig.getProtocol());

        ValidationUtils.throwIfBlank(mailConfig.getHost(), "邮件配置错误：服务器地址不能为空");
        sender.setHost(mailConfig.getHost());

        ValidationUtils.throwIfNull(mailConfig.getPort(), "邮件配置错误：服务器端口不能为空");
        sender.setPort(mailConfig.getPort());

        ValidationUtils.throwIfBlank(mailConfig.getUsername(), "邮件配置错误：用户名不能为空");
        sender.setUsername(mailConfig.getUsername());

        ValidationUtils.throwIfBlank(mailConfig.getPassword(), "邮件配置错误：密码不能为空");
        sender.setPassword(mailConfig.getPassword());

        if (mailConfig.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(mailConfig.getDefaultEncoding().name());
        }

        if (!mailConfig.getProperties().isEmpty()) {
            sender.setJavaMailProperties(mailConfig.toJavaMailProperties());
        }
    }
}
