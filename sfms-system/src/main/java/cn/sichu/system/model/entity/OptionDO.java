package cn.sichu.system.model.entity;

import cn.sichu.crud.mp.model.entity.BaseUpdateDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_option")
public class OptionDO extends BaseUpdateDO {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 类别
     **/
    private String category;

    /**
     * 名称
     **/
    private String name;

    /**
     * 键
     **/
    private String code;

    /**
     * 值
     **/
    private String value;

    /**
     * 默认值
     **/
    private String defaultValue;

    /**
     * 描述
     **/
    private String description;
}
