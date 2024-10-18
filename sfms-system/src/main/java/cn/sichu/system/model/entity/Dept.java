package cn.sichu.system.model.entity;

import cn.sichu.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 部门实体
 *
 * @author sichu huang
 * @since 2024/10/16 22:32
 */
@Getter
@Setter
@TableName("sys_dept")
public class Dept extends BaseEntity {
    
    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 父节点id路径
     */
    private String treePath;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态(1-正常 0-禁用)
     */
    private Integer status;
}
