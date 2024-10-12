package cn.sichu.system.model.entity;

import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.enums.DisEnableStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 部门实体类
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_dept")
public class DeptDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    private String name;

    /**
     * 上级部门ID
     **/
    private Long parentId;

    /**
     * 祖级列表
     **/
    private String ancestors;

    /**
     * 描述
     **/
    private String description;

    /**
     * 排序
     **/
    private Integer sort;

    /**
     * 状态
     **/
    private DisEnableStatusEnum status;

    /**
     * 是否为系统内置数据
     **/
    private Boolean isSystem;
}
