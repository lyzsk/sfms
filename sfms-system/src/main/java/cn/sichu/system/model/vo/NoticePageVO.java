package cn.sichu.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sichu huang
 * @since 2024/10/16 23:05
 */
@Getter
@Setter
@Schema(description = "通知公告视图对象")
public class NoticePageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知状态")
    private Integer publishStatus;

    @Schema(description = "通知类型")
    private Integer type;

    @Schema(description = "发布人姓名")
    private String publisherName;

    @Schema(description = "通知等级")
    private String level;

    @Schema(description = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime publishTime;

    @Schema(description = "是否已读")
    private Integer isRead;

    @Schema(description = "目标类型")
    private Integer targetType;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createTime;

    @Schema(description = "撤回时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime revokeTime;
}
