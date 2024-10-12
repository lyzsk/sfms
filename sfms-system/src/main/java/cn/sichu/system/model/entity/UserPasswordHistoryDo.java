package cn.sichu.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@NoArgsConstructor
@TableName("t_sys_user_password_history")
public class UserPasswordHistoryDo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     **/
    private Long userId;

    /**
     * 密码
     **/
    private String password;
    
    /**
     * 创建时间
     **/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    public UserPasswordHistoryDo(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
