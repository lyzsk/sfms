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
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "用户查询条件")
public class UserQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关键词
     **/
    @Schema(description = "关键词", example = "zhangsan")
    private String description;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;

    /**
     * 创建时间
     **/
    @Schema(description = "创建时间", example = "2023-08-08 00:00:00,2023-08-08 23:59:59.000")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_MS_PATTERN)
    @Size(max = 2, message = "创建时间必须是一个范围")
    private List<Date> createTime;

    /**
     * 部门 ID
     **/
    @Schema(description = "部门 ID", example = "1")
    private Long deptId;
}
