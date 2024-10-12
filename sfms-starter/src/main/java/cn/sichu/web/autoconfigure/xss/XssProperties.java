package cn.sichu.web.autoconfigure.xss;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.web.enums.XssMode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * XSS 过滤配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.WEB_XSS)
public class XssProperties {

    /**
     * 是否启用 XSS 过滤
     */
    private boolean enabled = true;

    /**
     * 拦截路由（默认为空）
     *
     * <p>
     * 当拦截的路由配置不为空，则根据该配置执行过滤
     * </p>
     */
    private List<String> includePatterns = new ArrayList<>();

    /**
     * 放行路由（默认为空）
     */
    private List<String> excludePatterns = new ArrayList<>();

    /**
     * XSS 模式
     */
    private XssMode mode = XssMode.CLEAN;

}
