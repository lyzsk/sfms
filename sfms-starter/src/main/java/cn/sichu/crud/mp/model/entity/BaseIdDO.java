package cn.sichu.crud.mp.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类基类
 *
 * <p>
 * 通用字段：ID 主键
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class BaseIdDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

}
