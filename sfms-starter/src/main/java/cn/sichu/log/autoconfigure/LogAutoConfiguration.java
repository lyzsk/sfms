package cn.sichu.log.autoconfigure;

import cn.sichu.log.dao.LogDao;
import cn.sichu.log.dao.impl.LogDaoDefaultImpl;
import cn.sichu.log.handler.LogFilter;
import cn.sichu.log.handler.LogInterceptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Configuration
@ConditionalOnEnabledLog
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LogAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(LogAutoConfiguration.class);
    private final LogProperties logProperties;

    public LogAutoConfiguration(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor(logDao(), logProperties));
    }

    /**
     * 日志过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public LogFilter logFilter() {
        return new LogFilter(logProperties);
    }

    /**
     * 日志持久层接口
     */
    @Bean
    @ConditionalOnMissingBean
    public LogDao logDao() {
        return new LogDaoDefaultImpl();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Log' completed initialization.");
    }
}
