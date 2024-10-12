package cn.sichu.system.model.query;

import cn.sichu.data.core.annotation.Query;
import cn.sichu.data.core.enums.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "字典查询条件")
public class DictQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    @Query(columns = {"name", "code", "description"}, type = QueryType.LIKE)
    private String description;
}
