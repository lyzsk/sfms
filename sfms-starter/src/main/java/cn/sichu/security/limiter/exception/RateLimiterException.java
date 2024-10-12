package cn.sichu.security.limiter.exception;

import cn.sichu.core.exception.BaseException;

/**
 * 限流异常
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class RateLimiterException extends BaseException {

    public RateLimiterException(String message) {
        super(message);
    }

    public RateLimiterException(String message, Throwable cause) {
        super(message, cause);
    }
}
