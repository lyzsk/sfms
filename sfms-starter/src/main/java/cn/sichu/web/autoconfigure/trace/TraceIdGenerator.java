package cn.sichu.web.autoconfigure.trace;

import com.yomahub.tlog.id.TLogIdGenerator;
import com.yomahub.tlog.id.snowflake.UniqueIdGenerator;

/**
 * TLog ID 生成器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class TraceIdGenerator extends TLogIdGenerator {
    @Override
    public String generateTraceId() {
        return String.valueOf(UniqueIdGenerator.generateId());
    }
}