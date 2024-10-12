package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.enums.QueryType;
import cn.sichu.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门查询条件
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "部门查询条件")
public class DeptQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关键词
     **/
    @Schema(description = "关键词", example = "测试部")
    @Query(columns = {"name", "description"}, type = QueryType.LIKE)
    private String description;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;
}
