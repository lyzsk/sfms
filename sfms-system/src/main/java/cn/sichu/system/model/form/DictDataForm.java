package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典数据表单对象
 *
 * @author sichu huang
 * @since 2024/10/16 23:31
 */
@Data
@Schema(description = "字典数据表单")
public class DictDataForm {

    @Schema(description = "字典ID")
    private Long id;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（1-启用，0-禁用）")
    private Integer status;

    @Schema(description = "字典类型")
    private String tagType;
}
