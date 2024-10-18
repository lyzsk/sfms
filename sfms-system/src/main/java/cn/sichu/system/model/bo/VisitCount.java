package cn.sichu.system.model.bo;

import lombok.Data;

/**
 * 特定日期访问统计
 *
 * @author sichu huang
 * @since 2024/10/16 22:46
 */
@Data
public class VisitCount {
    /**
     * 日期 yyyy-MM-dd HH:mm:ss.SSS
     */
    private String date;

    /**
     * 访问次数
     */
    private Integer count;
}
