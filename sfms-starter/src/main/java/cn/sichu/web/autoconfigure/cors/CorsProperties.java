package cn.sichu.web.autoconfigure.cors;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.core.constant.StringConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 跨域配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.WEB_CORS)
public class CorsProperties {

    private static final List<String> ALL = Collections.singletonList(StringConstants.ASTERISK);

    /**
     * 是否启用跨域配置
     **/
    private boolean enabled = false;

    /**
     * 允许跨域的域名
     **/
    private List<String> allowedOrigins = new ArrayList<>(ALL);

    /**
     * 允许跨域的请求方式
     **/
    private List<String> allowedMethods = new ArrayList<>(ALL);

    /**
     * 允许跨域的请求头
     **/
    private List<String> allowedHeaders = new ArrayList<>(ALL);

    /**
     * 允许跨域的响应头
     **/
    private List<String> exposedHeaders = new ArrayList<>();

}
