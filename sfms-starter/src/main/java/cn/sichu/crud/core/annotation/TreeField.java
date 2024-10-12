package cn.sichu.crud.core.annotation;

import java.lang.annotation.*;

/**
 * 树结构字段
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeField {

    /**
     * ID 字段名
     *
     * @return ID 字段名
     */
    String value() default "key";

    /**
     * 父 ID 字段名
     *
     * @return 父 ID 字段名
     */
    String parentIdKey() default "parentId";

    /**
     * 名称字段名
     *
     * @return 名称字段名
     */
    String nameKey() default "title";

    /**
     * 排序字段名
     *
     * @return 排序字段名
     */
    String weightKey() default "sort";

    /**
     * 子列表字段名
     *
     * @return 子列表字段名
     */
    String childrenKey() default "children";

    /**
     * 递归深度（< 0 不限制）
     *
     * @return 递归深度
     */
    int deep() default -1;
}
