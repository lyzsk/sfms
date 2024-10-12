package cn.sichu.security.limiter.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 限流器配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.SECURITY_LIMITER)
public class RateLimiterProperties {

    /**
     * Key 前缀
     */
    private String keyPrefix = "RateLimiter";

}
