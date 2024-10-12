package cn.sichu.security.limiter.core;

import java.lang.reflect.Method;

/**
 * 限流器名称生成器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@FunctionalInterface
public interface RateLimiterNameGenerator {

    /**
     * Generate a rate limiter name for the given method and its parameters.
     *
     * @param target the target instance
     * @param method the method being called
     * @param args   the method parameters (with any var-args expanded)
     * @return a generated rate limiter name
     */
    String generate(Object target, Method method, Object... args);
}
