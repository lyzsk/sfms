package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改邮箱表单
 *
 * @author sichu huang
 * @since 2024/10/16 22:51
 */
@Data
@Schema(description = "修改邮箱表单")
public class EmailChangeForm {
    @Schema(description = "原密码")
    private String email;

    @Schema(description = "验证码")
    private String code;
}
