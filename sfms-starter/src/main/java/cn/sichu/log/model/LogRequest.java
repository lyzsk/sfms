package cn.sichu.log.model;

import cn.hutool.core.text.CharSequenceUtil;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.core.utils.IpUtils;
import cn.sichu.log.enums.Include;
import cn.sichu.web.utils.ServletUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * 请求信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class LogRequest {

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求 URL
     */
    private URI url;

    /**
     * IP
     */
    private String ip;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求体（JSON 字符串）
     */
    private String body;

    /**
     * 请求参数
     */
    private Map<String, Object> param;

    /**
     * IP 归属地
     */
    private String address;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    public LogRequest(RecordableHttpRequest request, Set<Include> includes) {
        this.method = request.getMethod();
        this.url = request.getUrl();
        this.ip = request.getIp();
        this.headers = (includes.contains(Include.REQUEST_HEADERS)) ? request.getHeaders() : null;
        if (includes.contains(Include.REQUEST_BODY)) {
            this.body = request.getBody();
        } else if (includes.contains(Include.REQUEST_PARAM)) {
            this.param = request.getParam();
        }
        this.address = (includes.contains(Include.IP_ADDRESS)) ?
            ExceptionUtils.exToNull(() -> IpUtils.getIpv4Address(this.ip)) : null;
        if (null == this.headers) {
            return;
        }
        String userAgentString = this.headers.entrySet().stream()
            .filter(h -> HttpHeaders.USER_AGENT.equalsIgnoreCase(h.getKey()))
            .map(Map.Entry::getValue).findFirst().orElse(null);
        if (CharSequenceUtil.isNotBlank(userAgentString)) {
            this.browser =
                (includes.contains(Include.BROWSER)) ? ServletUtils.getBrowser(userAgentString) :
                    null;
            this.os = (includes.contains(Include.OS)) ? ServletUtils.getOs(userAgentString) : null;
        }
    }

}