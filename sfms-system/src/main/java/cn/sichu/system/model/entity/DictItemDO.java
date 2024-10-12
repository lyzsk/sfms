package cn.sichu.system.model.entity;

import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.enums.DisEnableStatusEnum;
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
@TableName("t_sys_dict_item")
public class DictItemDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签
     **/
    private String label;

    /**
     * 值
     **/
    private String value;

    /**
     * 标签颜色
     **/
    private String color;

    /**
     * 排序
     **/
    private Integer sort;

    /**
     * 描述
     **/
    private String description;

    /**
     * 状态
     **/
    private DisEnableStatusEnum status;

    /**
     * 字典ID
     **/
    private Long dictId;
}
