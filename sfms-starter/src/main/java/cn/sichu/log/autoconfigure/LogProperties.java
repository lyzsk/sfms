package cn.sichu.log.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.log.enums.Include;
import cn.sichu.web.utils.SpringWebUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 日志配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.LOG)
public class LogProperties {

    /**
     * 是否启用日志
     */
    private boolean enabled = true;

    /**
     * 是否打印日志，开启后可打印访问日志（类似于 Nginx access log）
     * <p>
     * 不记录日志也支持开启打印访问日志
     * </p>
     */
    private Boolean isPrint = false;

    /**
     * 包含信息
     */
    private Set<Include> includes = Include.defaultIncludes();

    /**
     * 放行路由
     */
    private List<String> excludePatterns = new ArrayList<>();

    /**
     * 是否匹配放行路由
     *
     * @param uri 请求 URI
     * @return 是否匹配
     */
    public boolean isMatch(String uri) {
        return this.getExcludePatterns().stream()
            .anyMatch(pattern -> SpringWebUtils.isMatch(uri, pattern));
    }
}
