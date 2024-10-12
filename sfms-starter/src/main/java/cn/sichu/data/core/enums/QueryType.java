package cn.sichu.data.core.enums;

/**
 * 查询类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public enum QueryType {

    /**
     * 等于 =，例如：WHERE age = 18
     */
    EQ,

    /**
     * 不等于 !=，例如：WHERE age != 18
     */
    NE,

    /**
     * 大于 >，例如：WHERE age > 18
     */
    GT,

    /**
     * 大于等于 >= ，例如：WHERE age >= 18
     */
    GE,

    /**
     * 小于 <，例如：WHERE age < 18
     */
    LT,

    /**
     * 小于等于 <=，例如：WHERE age <= 18
     */
    LE,

    /**
     * 范围查询，例如：WHERE age BETWEEN 10 AND 18
     */
    BETWEEN,

    /**
     * LIKE '%值%'，例如：WHERE nickname LIKE '%s%'
     */
    LIKE,

    /**
     * LIKE '%值'，例如：WHERE nickname LIKE '%s'
     */
    LIKE_LEFT,

    /**
     * LIKE '值%'，例如：WHERE nickname LIKE 's%'
     */
    LIKE_RIGHT,

    /**
     * 包含查询，例如：WHERE age IN (10, 20, 30)
     */
    IN,

    /**
     * 不包含查询，例如：WHERE age NOT IN (20, 30)
     */
    NOT_IN,

    /**
     * 空查询，例如：WHERE email IS NULL
     */
    IS_NULL,

    /**
     * 非空查询，例如：WHERE email IS NOT NULL
     */
    IS_NOT_NULL,
    ;
}
