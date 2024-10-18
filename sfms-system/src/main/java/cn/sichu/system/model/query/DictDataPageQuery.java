package cn.sichu.system.model.query;

import cn.sichu.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sichu huang
 * @since 2024/10/16 22:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典数据分页查询对象")
public class DictDataPageQuery extends BasePageQuery {

    @Schema(description = "关键字(字典数据标签/值)")
    private String keywords;

    @Schema(description = "字典编码")
    private String dictCode;
}
