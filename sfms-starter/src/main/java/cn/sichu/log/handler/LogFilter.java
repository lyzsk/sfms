package cn.sichu.log.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.log.autoconfigure.LogProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * 日志过滤器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class LogFilter extends OncePerRequestFilter implements Ordered {

    private final LogProperties logProperties;

    public LogFilter(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        if (!this.isFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        boolean isMatch = logProperties.isMatch(request.getRequestURI());
        // 包装输入流，可重复读取
        if (!isMatch && this.isRequestWrapper(request)) {
            request = new ContentCachingRequestWrapper(request);
        }
        // 包装输出流，可重复读取
        boolean isResponseWrapper = !isMatch && this.isResponseWrapper(response);
        if (isResponseWrapper) {
            response = new ContentCachingResponseWrapper(response);
        }
        filterChain.doFilter(request, response);
        // 更新响应（不操作这一步，会导致接口响应空白）
        if (isResponseWrapper) {
            this.updateResponse(response);
        }
    }

    /**
     * 是否过滤请求
     *
     * @param request 请求对象
     * @return 是否过滤请求
     */
    private boolean isFilter(HttpServletRequest request) {
        if (!isRequestValid(request)) {
            return false;
        }
        // 不拦截 /error
        ServerProperties serverProperties = SpringUtil.getBean(ServerProperties.class);
        return !request.getRequestURI().equals(serverProperties.getError().getPath());
    }

    /**
     * 请求是否有效
     *
     * @param request 请求对象
     * @return true：是；false：否
     */
    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * 是否需要包装输入流
     *
     * @param request 请求对象
     * @return true：是；false：否
     */
    private boolean isRequestWrapper(HttpServletRequest request) {
        return !(request instanceof ContentCachingRequestWrapper);
    }

    /**
     * 是否需要包装输出流
     *
     * @param response 响应对象
     * @return true：是；false：否
     */
    private boolean isResponseWrapper(HttpServletResponse response) {
        return !(response instanceof ContentCachingResponseWrapper);
    }

    /**
     * 更新响应
     *
     * @param response 响应对象
     * @throws IOException /
     */
    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }
}
