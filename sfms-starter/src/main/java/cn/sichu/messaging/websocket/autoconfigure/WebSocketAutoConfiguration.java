package cn.sichu.messaging.websocket.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.messaging.websocket.core.WebSocketClientService;
import cn.sichu.messaging.websocket.core.WebSocketInterceptor;
import cn.sichu.messaging.websocket.dao.WebSocketSessionDao;
import cn.sichu.messaging.websocket.dao.WebSocketSessionDaoDefaultImpl;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@EnableWebSocket
@EnableConfigurationProperties(WebSocketProperties.class)
@ConditionalOnProperty(prefix = PropertiesConstants.MESSAGING_WEBSOCKET,
    name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@Component
public class WebSocketAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebSocketAutoConfiguration.class);
    private final WebSocketProperties properties;

    public WebSocketAutoConfiguration(WebSocketProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WebSocketConfigurer webSocketConfigurer(WebSocketHandler handler,
        HandshakeInterceptor interceptor) {
        return registry -> registry.addHandler(handler, properties.getPath())
            .addInterceptors(interceptor)
            .setAllowedOrigins(properties.getAllowedOrigins().toArray(String[]::new));
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketHandler webSocketHandler(WebSocketSessionDao webSocketSessionDao) {
        return new cn.sichu.messaging.websocket.core.WebSocketHandler(properties,
            webSocketSessionDao);
    }

    @Bean
    @ConditionalOnMissingBean
    public HandshakeInterceptor handshakeInterceptor(
        WebSocketClientService webSocketClientService) {
        return new WebSocketInterceptor(properties, webSocketClientService);
    }

    /**
     * WebSocket 会话 DAO
     */
    @Bean
    @ConditionalOnMissingBean
    public WebSocketSessionDao webSocketSessionDao() {
        return new WebSocketSessionDaoDefaultImpl();
    }

    /**
     * WebSocket 客户端服务（如不提供，则报错）
     */
    @Bean
    @ConditionalOnMissingBean
    public WebSocketClientService webSocketClientService() {
        throw new NoSuchBeanDefinitionException(WebSocketClientService.class);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Messaging-WebSocket' completed initialization.");
    }
}
