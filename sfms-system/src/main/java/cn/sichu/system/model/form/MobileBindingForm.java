package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改手机表单
 *
 * @author sichu huang
 * @since 2024/10/16 22:52
 */
@Data
@Schema(description = "修改手机表单")
public class MobileBindingForm {
    @Schema(description = "原密码")
    private String mobile;

    @Schema(description = "验证码")
    private String code;
}
