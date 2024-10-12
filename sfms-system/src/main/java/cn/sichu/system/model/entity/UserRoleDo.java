package cn.sichu.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@NoArgsConstructor
@TableName("t_sys_user_role")
public class UserRoleDo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     **/
    private Long userId;
    
    /**
     * 角色 ID
     **/
    private Long roleId;

    public UserRoleDo(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
