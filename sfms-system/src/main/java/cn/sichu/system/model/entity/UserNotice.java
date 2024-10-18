package cn.sichu.system.model.entity;

import cn.sichu.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户通知公告实体对象
 *
 * @author sichu huang
 * @since 2024/10/16 22:40
 */
@Getter
@Setter
@TableName("sys_user_notice")
public class UserNotice extends BaseEntity {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 公共通知id
     */
    private Long noticeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 读取状态，0未读，1已读
     */
    private Integer isRead;

    /**
     * 用户阅读时间
     */
    private LocalDateTime readTime;
}
