package cn.sichu.system.model.req;

import cn.sichu.crud.core.model.req.BaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 修改参数信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "修改参数信息")
public class OptionReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    @Schema(description = "ID", example = "1")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 键
     **/
    @Schema(description = "键", example = "site_title")
    @NotBlank(message = "键不能为空")
    @Length(max = 100, message = "键长度不能超过 {max} 个字符")
    private String code;

    /**
     * 值
     **/
    @Schema(description = "值", example = "SFMS")
    @NotBlank(message = "值不能为空")
    private String value;
}
