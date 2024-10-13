package cn.sichu.messaging.mail.autoconfigure;

import cn.sichu.core.utils.GeneralPropertySourceFactory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 邮件自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@PropertySource(value = "classpath:default-messaging-mail.yml",
    factory = GeneralPropertySourceFactory.class)
@Component
public class MailAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MailAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Mail' completed initialization.");
    }
}
