package cn.sichu.system.model.entity;

import cn.sichu.crud.core.annotation.DictField;
import cn.sichu.crud.mp.model.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 字典实体
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@DictField(valueKey = "code")
@TableName("t_sys_dict")
public class DictDO extends BaseDO {

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
     * 描述
     **/
    private String description;

    /**
     * 是否为系统内置数据
     **/
    private Boolean isSystem;
}