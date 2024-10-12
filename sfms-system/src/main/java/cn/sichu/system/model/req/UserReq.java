package cn.sichu.system.model.req;

import cn.hutool.core.lang.RegexPool;
import cn.sichu.constant.RegexConstants;
import cn.sichu.crud.core.model.req.BaseReq;
import cn.sichu.crud.core.utils.ValidateGroup;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.List;

/**
 * 创建或修改用户信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建或修改用户信息")
public class UserReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     **/
    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = RegexConstants.USERNAME,
        message = "用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头")
    private String username;

    /**
     * 昵称
     **/
    @Schema(description = "昵称", example = "张三")
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME,
        message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    private String nickname;

    /**
     * 密码（加密）
     **/
    @Schema(description = "密码（加密）",
        example = "E7c72TH+LDxKTwavjM99W1MdI9Lljh79aPKiv3XB9MXcplhm7qJ1BJCj28yaflbdVbfc366klMtjLIWQGqb0qw==")
    @NotBlank(message = "密码不能为空", groups = ValidateGroup.Crud.Add.class)
    private String password;

    /**
     * 邮箱
     **/
    @Schema(description = "邮箱", example = "123456789@qq.com")
    @Pattern(regexp = "^$|" + RegexPool.EMAIL, message = "邮箱格式错误")
    @Length(max = 255, message = "邮箱长度不能超过 {max} 个字符")
    private String email;

    /**
     * 手机号码
     **/
    @Schema(description = "手机号码", example = "13811111111")
    @Pattern(regexp = "^$|" + RegexPool.MOBILE, message = "手机号码格式错误")
    private String phone;

    /**
     * 性别
     **/
    @Schema(description = "性别", example = "1")
    @NotNull(message = "性别非法")
    private GenderEnum gender;

    /**
     * 所属部门
     **/
    @Schema(description = "所属部门", example = "5")
    @NotNull(message = "所属部门不能为空")
    private Long deptId;

    /**
     * 所属角色
     **/
    @Schema(description = "所属角色", example = "2")
    @NotEmpty(message = "所属角色不能为空")
    private List<Long> roleIds;

    /**
     * 描述
     **/
    @Schema(description = "描述", example = "张三描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;
}
