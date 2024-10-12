package cn.sichu.data.core.annotation;

import cn.sichu.data.core.enums.QueryType;

import java.lang.annotation.*;

/**
 * 查询注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    /**
     * 列名（注意：列名是数据库字段名，而不是实体类字段名。如果命名是数据库关键字的，请使用转义符包裹）
     *
     * <p>
     * columns 为空时，默认取值字段名（自动转换为下划线命名）；<br>
     * columns 不为空且 columns 长度大于 1，多个列查询条件之间为或关系（OR）。
     * </p>
     */
    String[] columns() default {};

    /**
     * 查询类型（等值查询、模糊查询、范围查询等）
     */
    QueryType type() default QueryType.EQ;
}
