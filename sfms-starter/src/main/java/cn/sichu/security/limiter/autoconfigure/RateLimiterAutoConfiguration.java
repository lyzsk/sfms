package cn.sichu.security.limiter.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.security.limiter.core.DefaultRateLimiterNameGenerator;
import cn.sichu.security.limiter.core.RateLimiterNameGenerator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 限流器自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@EnableConfigurationProperties(RateLimiterProperties.class)
@ComponentScan({"top.continew.starter.security.limiter.core"})
@ConditionalOnProperty(prefix = PropertiesConstants.SECURITY_LIMITER,
    name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@Component
public class RateLimiterAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterAutoConfiguration.class);

    /**
     * 限流器名称生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public RateLimiterNameGenerator nameGenerator() {
        return new DefaultRateLimiterNameGenerator();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Security-RateLimiter' completed initialization.");
    }
}
