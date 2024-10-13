package cn.sichu.log.dao;

import cn.sichu.log.model.LogRecord;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

/**
 * 日志持久层接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface LogDAO {

    /**
     * 查询日志列表
     *
     * @return 日志列表
     */
    default List<LogRecord> list() {
        return Collections.emptyList();
    }

    /**
     * 记录日志
     *
     * @param logRecord 日志信息
     */
    void add(LogRecord logRecord) throws ParseException;
}
