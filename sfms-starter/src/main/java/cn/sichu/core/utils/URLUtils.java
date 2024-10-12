package cn.sichu.core.utils;

import cn.hutool.http.HttpUtil;

/**
 * URL（Uniform Resource Locator）统一资源定位符相关工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class URLUtils {

    private URLUtils() {
    }

    /**
     * 提供的 URL 是否为 HTTP URL（协议包括："http"，"https"）
     *
     * @param url URL
     * @return 是否为 HTTP URL
     */
    public static boolean isHttpUrl(String url) {
        return HttpUtil.isHttp(url) || HttpUtil.isHttps(url);
    }
}
