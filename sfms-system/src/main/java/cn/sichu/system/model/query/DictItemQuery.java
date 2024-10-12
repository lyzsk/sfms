package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.enums.QueryType;
import cn.sichu.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "字典项查询条件")
public class DictItemQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关键词
     **/
    @Schema(description = "关键词")
    @Query(columns = {"label", "description"}, type = QueryType.LIKE)
    private String description;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;

    /**
     * 字典 ID
     **/
    @Schema(description = "字典 ID")
    private Long dictId;
}
