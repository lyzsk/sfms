package cn.sichu.system.core.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.sichu.constant.SecurityConstants;
import cn.sichu.system.core.utils.SecurityUtils;
import cn.sichu.system.service.LogService;
import cn.sichu.utils.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author sichu huang
 * @since 2024/10/16 21:51
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogAspect {
    private final LogService logService;
    private final HttpServletRequest request;

    @Pointcut("@annotation(cn.sichu.common.annotation.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut() && @annotation(logAnnotation)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint,
        cn.sichu.annotation.Log logAnnotation) throws Throwable {
        String requestURI = request.getRequestURI();

        Long userId = null;
        // 非登录请求获取用户ID，登录请求在登录成功后(joinPoint.proceed())获取用户ID
        if (!SecurityConstants.LOGIN_PATH.equals(requestURI)) {
            userId = SecurityUtils.getUserId();
        }

        TimeInterval timer = DateUtil.timer();
        // 执行方法
        Object proceed = joinPoint.proceed();
        long executionTime = timer.interval();

        // 创建日志记录
        cn.sichu.system.model.entity.Log log = new cn.sichu.system.model.entity.Log();
        log.setModule(logAnnotation.module());
        log.setContent(logAnnotation.value());
        log.setRequestUri(requestURI);
        // 登录方法需要在登录成功后获取用户ID
        if (userId == null) {
            userId = SecurityUtils.getUserId();
        }
        log.setCreateBy(userId);
        String ipAddr = IPUtils.getIpAddr(request);
        if (StrUtil.isNotBlank(ipAddr)) {
            log.setIp(ipAddr);
            String region = IPUtils.getRegion(ipAddr);
            // 中国|0|四川省|成都市|电信 解析省和市
            if (StrUtil.isNotBlank(region)) {
                String[] regionArray = region.split("\\|");
                if (regionArray.length > 2) {
                    log.setProvince(regionArray[2]);
                    log.setCity(regionArray[3]);
                }
            }
        }
        log.setExecutionTime(executionTime);
        // 获取浏览器和终端系统信息
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        // 系统信息
        log.setOs(userAgent.getOs().getName());
        // 浏览器信息
        log.setBrowser(userAgent.getBrowser().getName());
        log.setBrowserVersion(userAgent.getBrowser().getVersion(userAgentString));
        // 保存日志到数据库
        logService.save(log);

        return proceed;
    }
}
