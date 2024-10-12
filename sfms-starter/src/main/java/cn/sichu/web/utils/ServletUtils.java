package cn.sichu.web.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.sichu.core.constant.StringConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Map;

/**
 * Servlet 工具类
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public class ServletUtils {
    private ServletUtils() {
    }

    /**
     * 获取浏览器及其版本信息
     *
     * @param request 请求对象
     * @return java.lang.String 浏览器及其版本信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static String getBrowser(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        return getBrowser(request.getHeader("User-Agent"));
    }

    /**
     * 获取浏览器及其版本信息
     *
     * @param userAgentString User-Agent 字符串
     * @return java.lang.String 浏览器及其版本信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static String getBrowser(String userAgentString) {
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        return userAgent.getBrowser().getName() + StringConstants.SPACE + userAgent.getVersion();
    }

    /**
     * 获取操作系统
     *
     * @param request 请求对象
     * @return java.lang.String 操作系统
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static String getOs(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        return getOs(request.getHeader("User-Agent"));
    }

    /**
     * 获取操作系统
     *
     * @param userAgentString User-Agent 字符串
     * @return java.lang.String 操作系统
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static String getOs(String userAgentString) {
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        return userAgent.getOs().getName();
    }

    /**
     * 获取响应所有的头（header）信息
     *
     * @param response 响应对象
     * @return java.util.Map<java.lang.String, java.lang.String> header值
     * @author sichu huang
     * @date 2024/10/10
     **/
    public static Map<String, String> getHeaderMap(HttpServletResponse response) {
        final Collection<String> headerNames = response.getHeaderNames();
        final Map<String, String> headerMap = MapUtil.newHashMap(headerNames.size(), true);
        for (String name : headerNames) {
            headerMap.put(name, response.getHeader(name));
        }
        return headerMap;
    }
}
