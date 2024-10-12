package cn.sichu.system.model.req;

import cn.sichu.crud.core.model.req.BaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Date;

/**
 * 创建或修改公告信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建或修改公告信息")
public class NoticeReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "这是公告标题")
    @NotBlank(message = "标题不能为空")
    @Length(max = 150, message = "标题长度不能超过 {max} 个字符")
    private String title;

    /**
     * 内容
     **/
    @Schema(description = "内容", example = "这是公告内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 类型（取值于字典 notice_type）
     **/
    @Schema(description = "类型（取值于字典 notice_type）", example = "1")
    @NotBlank(message = "类型不能为空")
    @Length(max = 30, message = "类型长度不能超过 {max} 个字符")
    private String type;

    /**
     * 生效时间
     **/
    @Schema(description = "生效时间", example = "2023-08-08 00:00:00", type = "string")
    private Date effectiveTime;

    /**
     * 终止时间
     **/
    @Schema(description = "终止时间", example = "2023-08-08 23:59:59", type = "string")
    @Future(message = "终止时间必须是未来时间")
    private Date terminateTime;
}