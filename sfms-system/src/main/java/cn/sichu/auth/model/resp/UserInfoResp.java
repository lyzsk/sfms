package cn.sichu.auth.model.resp;

import cn.sichu.enums.GenderEnum;
import cn.sichu.security.mask.annotation.JsonMask;
import cn.sichu.security.mask.enums.MaskType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 用户信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@Schema(description = "用户信息")
public class UserInfoResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    private String nickname;

    /**
     * 性别
     */
    @Schema(description = "性别", example = "1")
    private GenderEnum gender;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "c*******@126.com")
    @JsonMask(MaskType.EMAIL)
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码", example = "188****8888")
    @JsonMask(MaskType.MOBILE_PHONE)
    private String phone;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址",
        example = "https://himg.bdimg.com/sys/portrait/item/public.1.81ac9a9e.rf1ix17UfughLQjNo7XQ_w.jpg")
    private String avatar;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    private String description;

    /**
     * 最后一次修改密码时间
     */
    @Schema(description = "最后一次修改密码时间", example = "2023-08-08 08:08:08.000",
        type = "string")
    private Date pwdResetTime;

    /**
     * 密码是否已过期
     */
    @Schema(description = "密码是否已过期", example = "true")
    private Boolean pwdExpired;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 注册日期
     */
    @Schema(description = "注册时间", example = "2023-08-08 00:00:00.000")
    private Date registrationTime;

    /**
     * 部门 ID
     */
    @Schema(description = "部门 ID", example = "1")
    private Long deptId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "测试部")
    private String deptName;

    /**
     * 权限码集合
     */
    @Schema(description = "权限码集合", example = "[\"system:user:list\",\"system:user:add\"]")
    private Set<String> permissions;

    /**
     * 角色编码集合
     */
    @Schema(description = "角色编码集合", example = "[\"test\"]")
    private Set<String> roles;

    public Date getRegistrationDate() {
        return createTime;
    }
}
