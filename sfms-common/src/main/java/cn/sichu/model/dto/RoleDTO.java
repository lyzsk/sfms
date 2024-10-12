package cn.sichu.model.dto;

import cn.sichu.enums.DataScopeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
public class RoleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    private Long id;

    /**
     * 角色编码
     **/
    private String code;

    /**
     * 数据权限
     **/
    private DataScopeEnum dataScope;
}
