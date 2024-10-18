package cn.sichu.system.model.entity;

import cn.sichu.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sichu huang
 * @since 2024/10/16 22:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class Dict extends BaseEntity {
    
    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 状态（1：启用, 0：停用）
     */
    private Integer status;
}
