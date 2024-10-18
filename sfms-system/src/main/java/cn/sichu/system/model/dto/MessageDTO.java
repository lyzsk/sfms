package cn.sichu.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * 消息载体
 *
 * @author sichu huang
 * @since 2024/10/16 22:43
 */
@Data
public class MessageDTO {
    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "发送者")
    private String sender;

    @Schema(description = "接收者")
    private Set<String> receivers;
}
