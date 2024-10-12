package cn.sichu.config.exception;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.sichu.core.exception.BadRequestException;
import cn.sichu.core.exception.BusinessException;
import cn.sichu.web.model.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Slf4j
@Order(99)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截业务异常
     *
     * @param e       e
     * @param request request
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/12
     **/
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURI(), e);
        return R.fail(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
    }

    /**
     * 拦截自定义验证异常-错误请求
     *
     * @param e       e
     * @param request request
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/12
     **/
    @ExceptionHandler(BadRequestException.class)
    public R handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURI(), e);
        return R.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
    }

    /**
     * 拦截文件上传异常-超过上传大小限制
     *
     * @param e       e
     * @param request request
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/12
     **/
    @ExceptionHandler(MultipartException.class)
    public R handleRequestTooBigException(MultipartException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURI(), e);
        String msg = e.getMessage();
        R defaultFail = R.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), msg);
        if (CharSequenceUtil.isBlank(msg)) {
            return defaultFail;
        }
        String sizeLimit;
        Throwable cause = e.getCause();
        if (null != cause) {
            msg = msg.concat(cause.getMessage().toLowerCase());
        }
        if (msg.contains("size") && msg.contains("exceed")) {
            sizeLimit = CharSequenceUtil.subBetween(msg, "the maximum size ", " for");
        } else if (msg.contains("larger than")) {
            sizeLimit = CharSequenceUtil.subAfter(msg, "larger than ", true);
        } else {
            return defaultFail;
        }
        String errorMsg =
            "请上传小于 %sKB 的文件".formatted(NumberUtil.parseLong(sizeLimit) / 1024);
        return R.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorMsg);
    }
}