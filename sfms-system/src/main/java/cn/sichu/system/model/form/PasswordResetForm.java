package cn.sichu.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 重置密码表单
 *
 * @author sichu huang
 * @since 2024/10/16 22:54
 */
@Data
@Schema(description = "重置密码表单")
public class PasswordResetForm {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "密码")
    private String password;
}
