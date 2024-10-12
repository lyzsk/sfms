package cn.sichu.crud.core.annotation;

import java.lang.annotation.*;

/**
 * 字典结构字段
 *
 * @author Charles7c
 * @since 2.1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictField {

    /**
     * 标签字段名
     *
     * @return 标签字段名
     */
    String labelKey() default "name";

    /**
     * 值字段名
     *
     * @return 值字段名
     */
    String valueKey() default "id";
}
