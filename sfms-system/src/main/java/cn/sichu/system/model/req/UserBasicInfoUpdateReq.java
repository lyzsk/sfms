package cn.sichu.system.model.req;

import cn.sichu.constant.RegexConstants;
import cn.sichu.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户基础信息修改信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@Schema(description = "用户基础信息修改信息")
public class UserBasicInfoUpdateReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     **/
    @Schema(description = "昵称", example = "张三")
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME,
        message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    private String nickname;

    /**
     * 性别
     **/
    @Schema(description = "性别", example = "1")
    @NotNull(message = "性别非法")
    private GenderEnum gender;
}
