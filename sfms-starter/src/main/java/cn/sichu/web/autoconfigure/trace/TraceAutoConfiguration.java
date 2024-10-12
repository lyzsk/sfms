package cn.sichu.web.autoconfigure.trace;

import cn.sichu.core.constant.PropertiesConstants;
import com.yomahub.tlog.id.TLogIdGenerator;
import com.yomahub.tlog.id.TLogIdGeneratorLoader;
import com.yomahub.tlog.spring.TLogPropertyInit;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

/**
 * 链路跟踪自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(TraceProperties.class)
@ConditionalOnProperty(prefix = PropertiesConstants.WEB_TRACE, name = PropertiesConstants.ENABLED,
    havingValue = "true")
public class TraceAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TraceAutoConfiguration.class);

    private final TraceProperties traceProperties;

    public TraceAutoConfiguration(TraceProperties traceProperties) {
        this.traceProperties = traceProperties;
    }

    @Bean
    @Primary
    public TLogPropertyInit tLogPropertyInit(TLogIdGenerator tLogIdGenerator) {
        TLogProperties tLogProperties = traceProperties.getTlog();
        TLogPropertyInit tLogPropertyInit = new TLogPropertyInit();
        tLogPropertyInit.setPattern(tLogProperties.getPattern());
        tLogPropertyInit.setEnableInvokeTimePrint(tLogProperties.getEnableInvokeTimePrint());
        tLogPropertyInit.setMdcEnable(tLogProperties.getMdcEnable());
        // 设置自定义 TraceId 生成器
        TLogIdGeneratorLoader.setIdGenerator(tLogIdGenerator);
        return tLogPropertyInit;
    }

    /**
     * TLog 过滤器配置
     */
    @Bean
    public FilterRegistrationBean<TLogServletFilter> tLogServletFilter() {
        FilterRegistrationBean<TLogServletFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TLogServletFilter(traceProperties));
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    /**
     * 自定义 Trace ID 生成器配置
     */
    @Bean
    @ConditionalOnMissingBean
    public TLogIdGenerator tLogIdGenerator() {
        return new TraceIdGenerator();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Web-Trace' completed initialization.");
    }
}
