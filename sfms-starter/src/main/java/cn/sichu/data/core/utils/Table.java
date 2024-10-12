package cn.sichu.data.core.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表信息
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class Table implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 注释
     */
    private String comment;

    /**
     * 存储引擎
     */
    private String engine;

    /**
     * 字符集
     */
    private String charset;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Table(String tableName) {
        this.tableName = tableName;
    }

}
