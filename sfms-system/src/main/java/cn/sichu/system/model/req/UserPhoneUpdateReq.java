package cn.sichu.system.model.req;

import cn.hutool.core.lang.RegexPool;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户手机号修改信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "用户手机号修改信息")
public class UserPhoneUpdateReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 新手机号
     **/
    @Schema(description = "新手机号", example = "13811111111")
    @NotBlank(message = "新手机号不能为空")
    @Pattern(regexp = RegexPool.MOBILE, message = "手机号格式错误")
    private String phone;

    /**
     * 验证码
     **/
    @Schema(description = "验证码", example = "8888")
    @NotBlank(message = "验证码不能为空")
    @Length(max = 4, message = "验证码非法")
    private String captcha;

    /**
     * 当前密码（加密）
     **/
    @Schema(description = "当前密码（加密）",
        example = "SYRLSszQGcMv4kP2Yolou9zf28B9GDakR9u91khxmR7V++i5A384kwnNZxqgvT6bjT4zqpIDuMFLWSt92hQJJA==")
    @NotBlank(message = "当前密码不能为空")
    private String oldPassword;
}
