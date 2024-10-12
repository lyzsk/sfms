package cn.sichu.crud.mp.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;

/**
 * 实体类基类
 *
 * <p>
 * 通用字段：创建人、创建时间
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class BaseCreateDO extends BaseIdDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
