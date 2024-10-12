package cn.sichu.web.autoconfigure.trace;

import cn.hutool.core.text.CharSequenceUtil;
import com.yomahub.tlog.context.TLogContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * TLog 过滤器
 *
 * <p>
 * 重写 TLog 配置以适配 Spring Boot 3.x
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class TLogServletFilter implements Filter {

    private final TraceProperties traceProperties;

    public TLogServletFilter(TraceProperties traceProperties) {
        this.traceProperties = traceProperties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest
            && response instanceof HttpServletResponse httpServletResponse) {
            try {
                TLogWebCommon.loadInstance().preHandle(httpServletRequest);
                // 把 traceId 放入 response 的 header，为了方便有些人有这样的需求，从前端拿整条链路的 traceId
                String traceIdName = traceProperties.getTraceIdName();
                if (CharSequenceUtil.isNotBlank(traceIdName)) {
                    httpServletResponse.addHeader(traceIdName, TLogContext.getTraceId());
                }
                chain.doFilter(request, response);
            } finally {
                TLogWebCommon.loadInstance().afterCompletion();
            }
            return;
        }
        chain.doFilter(request, response);
    }
}
