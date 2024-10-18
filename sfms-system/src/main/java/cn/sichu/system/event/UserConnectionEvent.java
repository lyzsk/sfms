package cn.sichu.system.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户连接事件
 *
 * @author sichu huang
 * @since 2024/10/17 15:39
 */
@Getter
public class UserConnectionEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private final String username;

    /**
     * 是否连接
     */
    private final boolean connected;

    /**
     * 用户连接事件
     *
     * @param source    事件源
     * @param username  用户名
     * @param connected 是否连接
     * @author sichu huang
     * @since 2024/10/17 15:39:37
     */
    public UserConnectionEvent(Object source, String username, boolean connected) {
        super(source);
        this.username = username;
        this.connected = connected;
    }
}
