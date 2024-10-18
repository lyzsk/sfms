package cn.sichu.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户公告VO
 *
 * @author sichu huang
 * @since 2024/10/16 23:06
 */
@Data
@Schema(description = "用户公告VO")
public class UserNoticePageVO {

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知类型")
    private Integer type;

    @Schema(description = "通知等级")
    private String level;

    @Schema(description = "发布人姓名")
    private String publisherName;

    @Schema(description = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime publishTime;

    @Schema(description = "是否已读")
    private Integer isRead;
}
