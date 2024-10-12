package cn.sichu.security.limiter.annotation;

import java.lang.annotation.*;

/**
 * 限流组注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiters {

    /**
     * 限流组
     */
    RateLimiter[] value();
}
