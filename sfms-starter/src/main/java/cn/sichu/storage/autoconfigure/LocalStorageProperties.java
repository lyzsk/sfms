package cn.sichu.storage.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地存储配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.STORAGE_LOCAL)
public class LocalStorageProperties {

    /**
     * 是否启用本地存储
     */
    private boolean enabled = true;

    /**
     * 存储映射
     */
    private Map<String, LocalStorageMapping> mapping = new HashMap<>();

    /**
     * 本地存储映射
     */
    @Setter
    @Getter
    public static class LocalStorageMapping {

        /**
         * 路径模式
         */
        private String pathPattern;

        /**
         * 资源路径
         */
        private String location;

        /**
         * 单文件上传大小限制
         */
        private DataSize maxFileSize = DataSize.ofMegabytes(1);

    }
}
