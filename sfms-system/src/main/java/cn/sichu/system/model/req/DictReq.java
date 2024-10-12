package cn.sichu.system.model.req;

import cn.sichu.constant.RegexConstants;
import cn.sichu.crud.core.model.req.BaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 创建或修改字典信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建或修改字典信息")
public class DictReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称", example = "公告类型")
    @NotBlank(message = "名称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME,
        message = "名称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "notice_type")
    @NotBlank(message = "编码不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_CODE,
        message = "编码长度为 2-30 个字符，支持大小写字母、数字、下划线，以字母开头")
    private String code;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "公告类型描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    private String description;
}