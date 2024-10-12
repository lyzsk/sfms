package cn.sichu.web.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.core.constant.StringConstants;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.PathContainer;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Spring Web 工具类
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public class SpringWebUtils {
    /**
     * 获取请求对象
     **/
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取响应对象
     **/
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 路径是否匹配
     *
     * @param path     路径
     * @param patterns 匹配模式列表
     * @return boolean
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static boolean isMatch(String path, List<String> patterns) {
        return patterns.stream().anyMatch(pattern -> isMatch(path, pattern));
    }

    /**
     * 路径是否匹配
     *
     * @param path     路径
     * @param patterns 匹配模式列表
     * @return boolean
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static boolean isMatch(String path, String... patterns) {
        return Arrays.stream(patterns).anyMatch(pattern -> isMatch(path, pattern));
    }

    /**
     * 路径是否匹配
     *
     * @param path    路径
     * @param pattern 匹配模式
     * @return boolean
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static boolean isMatch(String path, String pattern) {
        PathPattern pathPattern = PathPatternParser.defaultInstance.parse(pattern);
        PathContainer pathContainer = PathContainer.parsePath(path);
        return pathPattern.matches(pathContainer);
    }

    /**
     * 取消注册静态资源映射
     *
     * @param handlerMap 静态资源映射
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static void deRegisterResourceHandler(Map<String, String> handlerMap) {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        // 获取已经注册的映射
        final HandlerMapping resourceHandlerMapping =
            applicationContext.getBean("resourceHandlerMapping", HandlerMapping.class);
        final Map<String, Object> oldHandlerMap =
            (Map<String, Object>)ReflectUtil.getFieldValue(resourceHandlerMapping, "handlerMap");
        // 移除之前注册的映射
        for (Map.Entry<String, String> entry : handlerMap.entrySet()) {
            String pathPattern =
                CharSequenceUtil.appendIfMissing(entry.getKey(), StringConstants.PATH_PATTERN);
            oldHandlerMap.remove(pathPattern);
        }
    }

    /**
     * 注册静态资源映射
     *
     * @param handlerMap 静态资源映射
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static void registerResourceHandler(Map<String, String> handlerMap) {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        // 获取已经注册的映射
        final HandlerMapping resourceHandlerMapping =
            applicationContext.getBean("resourceHandlerMapping", HandlerMapping.class);
        final Map<String, Object> oldHandlerMap =
            (Map<String, Object>)ReflectUtil.getFieldValue(resourceHandlerMapping, "handlerMap");
        // 重新注册映射
        final ServletContext servletContext = applicationContext.getBean(ServletContext.class);
        final ContentNegotiationManager contentNegotiationManager =
            applicationContext.getBean("mvcContentNegotiationManager",
                ContentNegotiationManager.class);
        final UrlPathHelper urlPathHelper =
            applicationContext.getBean("mvcUrlPathHelper", UrlPathHelper.class);
        final ResourceHandlerRegistry resourceHandlerRegistry =
            new ResourceHandlerRegistry(applicationContext, servletContext,
                contentNegotiationManager, urlPathHelper);
        for (Map.Entry<String, String> entry : handlerMap.entrySet()) {
            // 移除之前注册的映射
            String pathPattern =
                CharSequenceUtil.appendIfMissing(entry.getKey(), StringConstants.PATH_PATTERN);
            oldHandlerMap.remove(pathPattern);
            // 重新注册映射
            String resourceLocations =
                CharSequenceUtil.appendIfMissing(entry.getValue(), StringConstants.SLASH);
            resourceHandlerRegistry.addResourceHandler(pathPattern)
                .addResourceLocations("file:" + resourceLocations);
        }
        final Map<String, ?> additionalUrlMap =
            ReflectUtil.<SimpleUrlHandlerMapping>invoke(resourceHandlerRegistry,
                "getHandlerMapping").getUrlMap();
        ReflectUtil.<Void>invoke(resourceHandlerMapping, "registerHandlers", additionalUrlMap);
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes)Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes());
    }
}
