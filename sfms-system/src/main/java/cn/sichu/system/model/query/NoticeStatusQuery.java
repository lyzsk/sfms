package cn.sichu.system.model.query;

import cn.sichu.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

/**
 * 用户公告状态分页查询对象
 *
 * @author sichu huang
 * @since 2024/10/16 22:59
 */
@Getter
@Setter
@Schema(description = "用户公告状态查询对象")
public class NoticeStatusQuery extends BasePageQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "公共通知id")
    private Long noticeId;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "读取状态，0未读，1已读取")
    private Long readStatus;

    @Schema(description = "用户阅读时间")
    private List<String> readTime;
}
