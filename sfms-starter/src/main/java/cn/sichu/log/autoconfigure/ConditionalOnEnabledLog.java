package cn.sichu.log.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

/**
 * 是否启用日志记录注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(prefix = PropertiesConstants.LOG, name = PropertiesConstants.ENABLED,
    havingValue = "true", matchIfMissing = true)
public @interface ConditionalOnEnabledLog {
}