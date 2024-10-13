package cn.sichu.web.autoconfigure.trace;

import cn.sichu.core.constant.PropertiesConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 链路跟踪配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.WEB_TRACE)
public class TraceProperties {

    /**
     * 是否启用链路跟踪配置
     **/
    private boolean enabled = false;

    /**
     * 链路 ID 名称
     **/
    private String traceIdName = "traceId";

    /**
     * TLog 配置
     **/
    @NestedConfigurationProperty
    private TLogProperties tlog;

}
