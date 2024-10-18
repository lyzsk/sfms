package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 字典表单对象
 *
 * @author sichu huang
 * @since 2024/10/16 22:51
 */
@Data
@Schema(description = "字典")
public class DictForm {
    @Schema(description = "字典ID", example = "1")
    private Long id;

    @Schema(description = "字典名称", example = "性别")
    private String name;

    @Schema(description = "字典编码", example = "gender")
    private String dictCode;

    @Schema(description = "字典状态（1-启用，0-禁用）", example = "1")
    @Range(min = 0, max = 1, message = "字典状态不正确")
    private Integer status;
}