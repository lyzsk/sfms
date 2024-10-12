package cn.sichu.system.model.entity;

import cn.sichu.system.enums.MessageTypeEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息实体
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@TableName("t_sys_message")
public class MessageDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    @TableId
    private Long id;

    /**
     * 标题
     **/
    private String title;

    /**
     * 内容
     **/
    private String content;

    /**
     * 类型（1：系统消息）
     **/
    private MessageTypeEnum type;

    /**
     * 创建人
     **/
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     **/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}