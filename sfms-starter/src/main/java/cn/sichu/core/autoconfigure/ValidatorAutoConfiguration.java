package cn.sichu.core.autoconfigure;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Properties;

/**
 * JSR 303 校验器自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@Component
public class ValidatorAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ValidatorAutoConfiguration.class);

    /**
     * Validator 失败立即返回模式配置
     *
     * <p>
     * 默认情况下会校验完所有字段，然后才抛出异常。
     * </p>
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        try (LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean()) {
            // 国际化
            factoryBean.setValidationMessageSource(messageSource);
            factoryBean.setProviderClass(HibernateValidator.class);
            Properties properties = new Properties();
            properties.setProperty("hibernate.validator.fail_fast", "true");
            factoryBean.setValidationProperties(properties);
            factoryBean.afterPropertiesSet();
            return factoryBean.getValidator();
        }
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Validator' completed initialization.");
    }
}
