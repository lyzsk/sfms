package cn.sichu.config.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.sichu.web.model.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局 SaToken 异常处理器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Slf4j
@Order(99)
@RestControllerAdvice
public class GlobalSaTokenExceptionHandler {

    /**
     * 认证异常-登录认证
     *
     * @param e       e
     * @param request request
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/12
     **/
    @ExceptionHandler(NotLoginException.class)
    public R handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURI(), e);
        String errorMsg = switch (e.getType()) {
            case NotLoginException.KICK_OUT -> "您已被踢下线";
            case NotLoginException.BE_REPLACED_MESSAGE -> "您已被顶下线";
            default -> "您的登录状态已过期，请重新登录";
        };
        return R.fail(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errorMsg);
    }

    /**
     * 认证异常-权限认证
     *
     * @param e       e
     * @param request request
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/12
     **/
    @ExceptionHandler(NotPermissionException.class)
    public R handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURI(), e);
        return R.fail(String.valueOf(HttpStatus.FORBIDDEN.value()),
            "没有访问权限，请联系管理员授权");
    }

    /**
     * 认证异常-角色认证
     *
     * @param e       e
     * @param request request
     * @return cn.sichu.web.model.R
     * @author sichu huang
     * @date 2024/10/12
     **/
    @ExceptionHandler(NotRoleException.class)
    public R handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURI(), e);
        return R.fail(String.valueOf(HttpStatus.FORBIDDEN.value()),
            "没有访问权限，请联系管理员授权");
    }
}