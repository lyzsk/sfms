package cn.sichu.messaging.websocket.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.core.constant.StringConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * WebSocket 配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.MESSAGING_WEBSOCKET)
public class WebSocketProperties {

    private static final List<String> ALL = Collections.singletonList(StringConstants.ASTERISK);

    /**
     * 是否启用 WebSocket
     */
    private boolean enabled = true;

    /**
     * 路径
     */
    private String path = StringConstants.SLASH + "websocket";

    /**
     * 允许跨域的域名
     */
    private List<String> allowedOrigins = new ArrayList<>(ALL);

    /**
     * 客户端 ID Key
     */
    private String clientIdKey = "CLIENT_ID";

}
