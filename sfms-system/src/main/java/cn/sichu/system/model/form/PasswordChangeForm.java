package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改密码表单
 *
 * @author sichu huang
 * @since 2024/10/16 22:54
 */
@Data
@Schema(description = "修改密码表单")
public class PasswordChangeForm {
    @Schema(description = "原密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;
}
