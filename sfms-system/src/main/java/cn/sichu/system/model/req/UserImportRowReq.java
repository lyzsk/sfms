package cn.sichu.system.model.req;

import cn.hutool.core.lang.RegexPool;
import cn.sichu.constant.RegexConstants;
import cn.sichu.crud.core.model.req.BaseReq;
import cn.sichu.crud.core.utils.ValidateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 用户导入行数据
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户导入行数据")
public class UserImportRowReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     **/
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = RegexConstants.USERNAME,
        message = "用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头")
    private String username;

    /**
     * 昵称
     **/
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME,
        message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    private String nickname;

    /**
     * 密码
     **/
    @NotBlank(message = "密码不能为空", groups = ValidateGroup.Crud.Add.class)
    private String password;

    /**
     * 部门名称
     **/
    @NotNull(message = "所属部门不能为空")
    private String deptName;

    /**
     * 角色
     **/
    @NotBlank(message = "所属角色不能为空")
    private String roleName;

    /**
     * 性别
     **/
    private String gender;

    /**
     * 邮箱
     **/
    @Pattern(regexp = "^$|" + RegexPool.EMAIL, message = "邮箱格式错误")
    @Length(max = 255, message = "邮箱长度不能超过 {max} 个字符")
    private String email;

    /**
     * 手机号码
     **/
    @Pattern(regexp = "^$|" + RegexPool.MOBILE, message = "手机号码格式错误")
    private String phone;

    /**
     * 描述
     **/
    private String description;
}
