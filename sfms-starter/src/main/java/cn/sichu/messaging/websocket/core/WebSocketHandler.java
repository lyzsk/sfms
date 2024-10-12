package cn.sichu.messaging.websocket.core;

import cn.hutool.core.convert.Convert;
import cn.sichu.messaging.websocket.autoconfigure.WebSocketProperties;
import cn.sichu.messaging.websocket.dao.WebSocketSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * WebSocket 处理器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private final WebSocketProperties webSocketProperties;
    private final WebSocketSessionDao webSocketSessionDao;

    public WebSocketHandler(WebSocketProperties webSocketProperties,
        WebSocketSessionDao webSocketSessionDao) {
        this.webSocketProperties = webSocketProperties;
        this.webSocketSessionDao = webSocketSessionDao;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
        throws Exception {
        String clientId = this.getClientId(session);
        log.info("WebSocket receive message. clientId: {}, message: {}.", clientId,
            message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String clientId = this.getClientId(session);
        webSocketSessionDao.add(clientId, session);
        log.info("WebSocket client connect successfully. clientId: {}.", clientId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String clientId = this.getClientId(session);
        webSocketSessionDao.delete(clientId);
        log.info("WebSocket client connect closed. clientId: {}.", clientId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
        throws IOException {
        String clientId = this.getClientId(session);
        if (session.isOpen()) {
            session.close();
        }
        webSocketSessionDao.delete(clientId);
    }

    /**
     * 获取客户端 ID
     *
     * @param session 会话
     * @return 客户端 ID
     */
    private String getClientId(WebSocketSession session) {
        return Convert.toStr(session.getAttributes().get(webSocketProperties.getClientIdKey()));
    }
}
