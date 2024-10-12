package cn.sichu.log.model;

import java.net.URI;
import java.util.Map;

/**
 * 可记录的 HTTP 请求信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface RecordableHttpRequest {

    /**
     * 获取请求方式
     *
     * @return 请求方式
     */
    String getMethod();

    /**
     * 获取 URL
     *
     * @return URL
     */
    URI getUrl();

    /**
     * 获取 IP
     *
     * @return IP
     */
    String getIp();

    /**
     * 获取请求头
     *
     * @return 请求头
     */
    Map<String, String> getHeaders();

    /**
     * 获取请求体
     *
     * @return 请求体
     */
    String getBody();

    /**
     * 获取请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getParam();
}
