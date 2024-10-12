package cn.sichu.web.autoconfigure.response;

import cn.sichu.web.utils.SpringWebUtils;
import com.feiniaojin.gracefulresponse.advice.lifecycle.exception.BeforeControllerAdviceProcess;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认回调处理器实现
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class DefaultBeforeControllerAdviceProcessImpl implements BeforeControllerAdviceProcess {

    private final Logger log =
        LoggerFactory.getLogger(DefaultBeforeControllerAdviceProcessImpl.class);
    private final GlobalResponseProperties globalResponseProperties;

    public DefaultBeforeControllerAdviceProcessImpl(
        GlobalResponseProperties globalResponseProperties) {
        this.globalResponseProperties = globalResponseProperties;
    }

    @Override
    public void call(Throwable throwable) {
        if (globalResponseProperties.isPrintExceptionInGlobalAdvice()) {
            HttpServletRequest request = SpringWebUtils.getRequest();
            log.error("[{}] {}", request.getMethod(), request.getRequestURI(), throwable);
        }
    }
}
