package cn.sichu.config.satoken;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 密码配置属性
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@Data
@Component
@ConfigurationProperties(prefix = "auth.password")
public class LoginPasswordProperties {

    /**
     * 排除（放行）路径配置
     **/
    private String[] excludes = new String[0];
}