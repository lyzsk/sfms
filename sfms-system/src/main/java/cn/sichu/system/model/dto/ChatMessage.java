package cn.sichu.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统消息体
 *
 * @author sichu huang
 * @since 2024/10/16 22:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    /**
     * 发送者
     */
    private String sender;

    /**
     * 消息内容
     */
    private String content;
}
