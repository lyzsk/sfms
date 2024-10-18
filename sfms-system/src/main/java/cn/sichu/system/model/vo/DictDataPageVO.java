package cn.sichu.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典数据分页对象
 *
 * @author sichu huang
 * @since 2024/10/16 23:03
 */
@Getter
@Setter
@Schema(description = "字典数据分页对象")
public class DictDataPageVO {

    @Schema(description = "字典数据ID")
    private Long id;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（1:启用，0:禁用）")
    private Integer status;
}
