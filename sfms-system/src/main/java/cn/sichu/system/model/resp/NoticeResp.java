package cn.sichu.system.model.resp;

import cn.sichu.crud.core.model.resp.BaseResp;
import cn.sichu.system.enums.NoticeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.text.ParseException;
import java.util.Date;

/**
 * 公告信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "公告信息")
public class NoticeResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "这是公告标题")
    private String title;

    /**
     * 类型（取值于字典 notice_type）
     **/
    @Schema(description = "类型（取值于字典 notice_type）", example = "1")
    private String type;

    /**
     * 生效时间
     **/
    @Schema(description = "生效时间", example = "2023-08-08 00:00:00.000", type = "string")
    private Date effectiveTime;

    /**
     * 终止时间
     **/
    @Schema(description = "终止时间", example = "2023-08-08 23:59:59.000", type = "string")
    private Date terminateTime;

    /**
     * 状态
     *
     * @return 公告状态
     **/
    @Schema(description = "状态", example = "1")
    public NoticeStatusEnum getStatus() throws ParseException {
        return NoticeStatusEnum.getStatus(effectiveTime, terminateTime);
    }
}