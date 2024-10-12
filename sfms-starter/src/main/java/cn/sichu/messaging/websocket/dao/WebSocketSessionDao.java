package cn.sichu.messaging.websocket.dao;

import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 会话 DAO
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface WebSocketSessionDao {

    /**
     * 添加会话
     *
     * @param key     会话 Key
     * @param session 会话信息
     */
    void add(String key, WebSocketSession session);

    /**
     * 删除会话
     *
     * @param key 会话 Key
     */
    void delete(String key);

    /**
     * 获取会话
     *
     * @param key 会话 Key
     * @return 会话信息
     */
    WebSocketSession get(String key);
}
