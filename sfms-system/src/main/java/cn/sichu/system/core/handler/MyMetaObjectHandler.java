package cn.sichu.system.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * mybatis-plus 字段自动填充
 *
 * @author sichu huang
 * @since 2024/10/16 21:52
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间
     *
     * @param metaObject 元数据
     * @author sichu huang
     * @since 2024/10/18 18:34:20
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime",
            () -> LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateTime",
            () -> LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), LocalDateTime.class);
    }

    /**
     * 更新填充更新时间
     *
     * @param metaObject 元数据
     * @author sichu huang
     * @since 2024/10/18 18:34:10
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime",
            () -> LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), LocalDateTime.class);
    }
}
