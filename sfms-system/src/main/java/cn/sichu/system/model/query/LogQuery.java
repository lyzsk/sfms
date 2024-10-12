package cn.sichu.system.model.query;

import cn.hutool.core.date.DatePattern;
import cn.sichu.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 日志查询条件
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "日志查询条件")
public class LogQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志描述
     **/
    @Schema(description = "日志描述", example = "新增数据")
    private String description;

    /**
     * 所属模块
     **/
    @Schema(description = "所属模块", example = "所属模块")
    private String module;

    /**
     * IP
     **/
    @Schema(description = "IP", example = "")
    private String ip;

    /**
     * 操作人
     **/
    @Schema(description = "操作人", example = "admin")
    private String createUserString;

    /**
     * 操作时间
     **/
    @Schema(description = "操作时间", example = "2023-08-08 00:00:00.000,2023-08-08 23:59:59.000")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @Size(max = 2, message = "操作时间必须是一个范围")
    private List<Date> createTime;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;
}
