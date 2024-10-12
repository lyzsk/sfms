package cn.sichu.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@NoArgsConstructor
@TableName("t_sys_role_dept")
public class RoleDeptDo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色 ID
     **/
    private Long roleId;
    
    /**
     * 部门 ID
     **/
    private Long deptId;

    public RoleDeptDo(Long roleId, Long deptId) {
        this.roleId = roleId;
        this.deptId = deptId;
    }
}
