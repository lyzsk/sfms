package cn.sichu.exception;

import cn.sichu.result.IResultCode;
import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

/**
 * 自定义业务异常
 *
 * @author sichu huang
 * @since 2024/10/16 22:13
 */
@Getter
public class BusinessException extends RuntimeException {
    public IResultCode resultCode;

    public BusinessException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Object... args) {
        super(formatMessage(message, args));
    }

    private static String formatMessage(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }
}
