package cn.sichu.security.crypto.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.security.crypto.core.MyBatisDecryptInterceptor;
import cn.sichu.security.crypto.core.MyBatisEncryptInterceptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 加/解密自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@EnableConfigurationProperties(CryptoProperties.class)
@ConditionalOnProperty(prefix = PropertiesConstants.SECURITY_CRYPTO,
    name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@Component
public class CryptoAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CryptoAutoConfiguration.class);
    private final CryptoProperties properties;

    public CryptoAutoConfiguration(CryptoProperties properties) {
        this.properties = properties;
    }

    /**
     * MyBatis 加密拦截器配置
     */
    @Bean
    @ConditionalOnMissingBean
    public MyBatisEncryptInterceptor myBatisEncryptInterceptor() {
        return new MyBatisEncryptInterceptor(properties);
    }

    /**
     * MyBatis 解密拦截器配置
     */
    @Bean
    @ConditionalOnMissingBean(MyBatisDecryptInterceptor.class)
    public MyBatisDecryptInterceptor myBatisDecryptInterceptor() {
        return new MyBatisDecryptInterceptor(properties);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Security-Crypto' completed initialization.");
    }
}
