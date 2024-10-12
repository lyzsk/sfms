package cn.sichu.system.model.entity;

import cn.sichu.crud.core.annotation.DictField;
import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.enums.DataScopeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@DictField
@TableName("t_sys_role")
public class RoleDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    private String name;

    /**
     * 编码
     **/
    private String code;

    /**
     * 数据权限
     **/
    private DataScopeEnum dataScope;

    /**
     * 描述
     **/
    private String description;

    /**
     * 排序
     **/
    private Integer sort;

    /**
     * 是否为系统内置数据
     **/
    private Boolean isSystem;

    /**
     * 菜单选择是否父子节点关联
     **/
    private Boolean menuCheckStrictly;

    /**
     * 部门选择是否父子节点关联
     **/
    private Boolean deptCheckStrictly;
}
