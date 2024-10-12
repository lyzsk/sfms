package cn.sichu.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色和菜单实体
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@NoArgsConstructor
@TableName("t_sys_role_menu")
public class RoleMenuDo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     **/
    private Long roleId;
    
    /**
     * 菜单ID
     **/
    private Long menuId;

    public RoleMenuDo(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
