package cn.sichu.core.exception;

import java.io.Serial;

/**
 * 自定义异常基类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
