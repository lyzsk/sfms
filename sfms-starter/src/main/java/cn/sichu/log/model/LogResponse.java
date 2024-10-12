package cn.sichu.log.model;

import cn.sichu.log.enums.Include;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

/**
 * 响应信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class LogResponse {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 响应头
     */
    private Map<String, String> headers;

    /**
     * 响应体（JSON 字符串）
     */
    private String body;

    /**
     * 响应参数
     */
    private Map<String, Object> param;

    public LogResponse(RecordableHttpResponse response, Set<Include> includes) {
        this.status = response.getStatus();
        this.headers = (includes.contains(Include.RESPONSE_HEADERS)) ? response.getHeaders() : null;
        if (includes.contains(Include.RESPONSE_BODY)) {
            this.body = response.getBody();
        } else if (includes.contains(Include.RESPONSE_PARAM)) {
            this.param = response.getParam();
        }
    }

}