package cn.sichu.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息和用户关联实体
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@TableName("t_sys_message_user")
public class MessageUserDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息 ID
     **/
    private Long messageId;

    /**
     * 用户 ID
     **/
    private Long userId;

    /**
     * 是否已读
     **/
    private Boolean isRead;

    /**
     * 读取时间
     **/
    private Date readTime;
}