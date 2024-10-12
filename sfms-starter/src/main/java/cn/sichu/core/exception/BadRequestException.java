package cn.sichu.core.exception;

import java.io.Serial;

/**
 * 自定义验证异常-错误请求
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class BadRequestException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
