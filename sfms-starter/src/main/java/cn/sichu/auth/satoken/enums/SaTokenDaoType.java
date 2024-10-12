package cn.sichu.auth.satoken.enums;

/**
 * SaToken 持久层类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public enum SaTokenDaoType {

    /**
     * 默认（内存）
     */
    DEFAULT,

    /**
     * Redis
     */
    REDIS,

    /**
     * 自定义
     */
    CUSTOM
}
