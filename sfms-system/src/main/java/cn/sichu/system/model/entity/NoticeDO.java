package cn.sichu.system.model.entity;

import cn.sichu.crud.mp.model.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 公告实体
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_notice")
public class NoticeDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     **/
    private String title;

    /**
     * 内容
     **/
    private String content;

    /**
     * 类型
     **/
    private String type;

    /**
     * 生效时间
     **/
    private Date effectiveTime;

    /**
     * 终止时间
     **/
    private Date terminateTime;
}