package cn.sichu.config;

import cn.sichu.config.property.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * MailConfig 配置类，用于手动配置和注入 JavaMailSender。
 * 通过读取 MailProperties 类中配置的邮件相关属性来初始化 JavaMailSender。
 * <p>
 * 手动注入的原因是为了避免在使用 application-dev.yml 或其他非 application.yml 配置文件时，
 * IDEA 提示无法找到 JavaMailSender 的 bean。
 *
 * @author sichu huang
 * @since 2024/10/17 16:35
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {

    private final MailProperties mailProperties;

    public MailConfig(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    /**
     * 创建并配置 JavaMailSender bean
     *
     * @return org.springframework.mail.javamail.JavaMailSender
     * @author sichu huang
     * @since 2024/10/17 16:35:53
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", mailProperties.getProperties().getSmtp().isAuth());
        properties.put("mail.smtp.starttls.enable",
            mailProperties.getProperties().getSmtp().getStarttls().isEnable());

        return mailSender;
    }
}
