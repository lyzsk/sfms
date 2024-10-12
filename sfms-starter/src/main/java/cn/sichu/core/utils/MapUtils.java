package cn.sichu.core.utils;

import java.util.Map;
import java.util.Properties;

/**
 * Map 工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class MapUtils {

    private MapUtils() {
    }

    /**
     * 转换为 Properties 对象
     *
     * @param source 数据源
     * @return Properties 对象
     */
    public static Properties toProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }
}
