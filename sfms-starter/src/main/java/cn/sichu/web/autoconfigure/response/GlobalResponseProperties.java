package cn.sichu.web.autoconfigure.response;

import cn.sichu.core.constant.PropertiesConstants;
import com.feiniaojin.gracefulresponse.GracefulResponseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 全局响应配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@ConfigurationProperties(PropertiesConstants.WEB_RESPONSE)
public class GlobalResponseProperties extends GracefulResponseProperties {
}
