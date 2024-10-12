package cn.sichu.core.exception;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class BusinessException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
