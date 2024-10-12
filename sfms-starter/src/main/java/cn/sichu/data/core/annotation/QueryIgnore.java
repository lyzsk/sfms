package cn.sichu.data.core.annotation;

import java.lang.annotation.*;

/**
 * 查询解析忽略注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryIgnore {
}
