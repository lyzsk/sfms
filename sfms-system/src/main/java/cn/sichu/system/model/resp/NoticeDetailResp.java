package cn.sichu.system.model.resp;

import cn.sichu.crud.core.model.resp.BaseDetailResp;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 公告详情信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
@Schema(description = "公告详情信息")
public class NoticeDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    @Schema(description = "标题", example = "这是公告标题")
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 内容
     **/
    @Schema(description = "内容", example = "这是公告内容")
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 类型（取值于字典 notice_type）
     **/
    @Schema(description = "类型（取值于字典 notice_type）", example = "1")
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 生效时间
     **/
    @Schema(description = "生效时间", example = "2023-08-08 00:00:00.000", type = "string")
    @ExcelProperty(value = "生效时间")
    private Date effectiveTime;

    /**
     * 终止时间
     **/
    @Schema(description = "终止时间", example = "2023-08-08 23:59:59.000", type = "string")
    @ExcelProperty(value = "终止时间")
    private Date terminateTime;
}