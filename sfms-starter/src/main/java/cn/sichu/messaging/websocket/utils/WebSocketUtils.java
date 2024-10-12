package cn.sichu.messaging.websocket.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.messaging.websocket.dao.WebSocketSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSocket 工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class WebSocketUtils {

    private static final Logger log = LoggerFactory.getLogger(WebSocketUtils.class);
    private static final WebSocketSessionDao SESSION_DAO =
        SpringUtil.getBean(WebSocketSessionDao.class);

    private WebSocketUtils() {
    }

    /**
     * 发送消息
     *
     * @param clientId 客户端 ID
     * @param message  消息内容
     */
    public static void sendMessage(String clientId, String message) {
        WebSocketSession session = SESSION_DAO.get(clientId);
        sendMessage(session, message);
    }

    /**
     * 发送消息
     *
     * @param session 会话
     * @param message 消息内容
     */
    public static void sendMessage(WebSocketSession session, String message) {
        sendMessage(session, new TextMessage(message));
    }

    /**
     * 发送消息
     *
     * @param session 会话
     * @param message 消息内容
     */
    public static void sendMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (session == null || !session.isOpen()) {
            log.warn("WebSocket session closed.");
            return;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            log.error("WebSocket send message failed. sessionId: {}.", session.getId(), e);
        }
    }
}
