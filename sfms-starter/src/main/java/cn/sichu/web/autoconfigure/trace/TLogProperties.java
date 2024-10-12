package cn.sichu.web.autoconfigure.trace;

import lombok.Getter;
import lombok.Setter;

/**
 * TLog 配置属性
 *
 * <p>
 * 重写 TLog 配置以适配 Spring Boot 3.x
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class TLogProperties {

    /**
     * 日志标签模板
     */
    private String pattern;

    /**
     * 自动打印调用参数和时间
     */
    private Boolean enableInvokeTimePrint;

    /**
     * 自定义 TraceId 生成器
     */
    private String idGenerator;

    /**
     * MDC 模式
     */
    private Boolean mdcEnable;

}
