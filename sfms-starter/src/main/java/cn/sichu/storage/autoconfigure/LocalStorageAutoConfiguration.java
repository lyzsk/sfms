package cn.sichu.storage.autoconfigure;

import cn.hutool.core.text.CharSequenceUtil;
import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.core.constant.StringConstants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

/**
 * 本地文件自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@EnableWebMvc
@AutoConfiguration
@EnableConfigurationProperties(LocalStorageProperties.class)
@ConditionalOnProperty(prefix = PropertiesConstants.STORAGE_LOCAL,
    name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@Component
public class LocalStorageAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(LocalStorageAutoConfiguration.class);
    private final LocalStorageProperties properties;

    public LocalStorageAutoConfiguration(LocalStorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Map<String, LocalStorageProperties.LocalStorageMapping> mappingMap =
            properties.getMapping();
        for (Map.Entry<String, LocalStorageProperties.LocalStorageMapping> mappingEntry : mappingMap.entrySet()) {
            LocalStorageProperties.LocalStorageMapping mapping = mappingEntry.getValue();
            String pathPattern = mapping.getPathPattern();
            String location = mapping.getLocation();
            if (CharSequenceUtil.isBlank(location)) {
                throw new IllegalArgumentException(
                    "Path pattern [%s] location is null.".formatted(pathPattern));
            }
            registry.addResourceHandler(
                    CharSequenceUtil.appendIfMissing(pathPattern, StringConstants.PATH_PATTERN))
                .addResourceLocations(
                    !location.startsWith("file:") ? "file:%s".formatted(this.format(location)) :
                        this.format(location)).setCachePeriod(0);
        }
    }

    private String format(String location) {
        return location.replace(StringConstants.BACKSLASH, StringConstants.SLASH);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SFMS] - Auto Configuration 'Storage-Local' completed initialization.");
    }
}
