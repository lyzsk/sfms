package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.annotation.QueryIgnore;
import cn.sichu.data.core.enums.QueryType;
import cn.sichu.system.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "消息查询条件")
public class MessageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    @Schema(description = "ID", example = "1")
    private Long id;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "欢迎注册 xxx")
    @Query(type = QueryType.LIKE)
    private String title;

    /**
     * 类型
     **/
    @Schema(description = "类型", example = "1")
    private MessageTypeEnum type;

    /**
     * 是否已读
     **/
    @Schema(description = "是否已读", example = "true")
    @QueryIgnore
    private Boolean isRead;

    /**
     * 用户 ID
     **/
    @Schema(hidden = true)
    @QueryIgnore
    private Long userId;
}