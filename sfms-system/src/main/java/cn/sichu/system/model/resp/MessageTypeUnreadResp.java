package cn.sichu.system.model.resp;

import cn.sichu.system.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 各类型未读消息信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "各类型未读消息信息")
public class MessageTypeUnreadResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     **/
    @Schema(description = "类型（1：系统消息）", example = "1")
    private MessageTypeEnum type;

    /**
     * 数量
     **/
    @Schema(description = "数量", example = "10")
    private Long count;
}