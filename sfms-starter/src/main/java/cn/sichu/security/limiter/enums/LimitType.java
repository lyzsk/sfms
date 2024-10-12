package cn.sichu.security.limiter.enums;

/**
 * 限流类型
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public enum LimitType {

    /**
     * 全局限流
     */
    DEFAULT,

    /**
     * 根据 IP 限流
     */
    IP,

    /**
     * 根据实例限流（支持集群多实例）
     */
    CLUSTER
}
