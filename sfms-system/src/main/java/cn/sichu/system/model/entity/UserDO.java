package cn.sichu.system.model.entity;

import cn.sichu.config.mybatis.BCryptEncryptor;
import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.enums.GenderEnum;
import cn.sichu.security.crypto.annotation.FieldEncrypt;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_user")
public class UserDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     **/
    private String username;

    /**
     * 昵称
     **/
    private String nickname;

    /**
     * 密码
     **/
    @FieldEncrypt(encryptor = BCryptEncryptor.class)
    private String password;

    /**
     * 性别
     **/
    private GenderEnum gender;

    /**
     * 邮箱
     **/
    @FieldEncrypt
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String email;

    /**
     * 手机号码
     **/
    @FieldEncrypt
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String phone;

    /**
     * 头像地址
     **/
    private String avatar;

    /**
     * 描述
     **/
    private String description;

    /**
     * 状态
     **/
    private DisEnableStatusEnum status;

    /**
     * 是否为系统内置数据
     **/
    private Boolean isSystem;

    /**
     * 最后一次修改密码时间
     **/
    private Date pwdResetTime;

    /**
     * 部门 ID
     **/
    private Long deptId;
}
