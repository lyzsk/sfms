package cn.sichu.security.password.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 密码编解码配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.SECURITY_PASSWORD)
public class PasswordEncoderProperties {

    /**
     * 是否启用密码编解码配置
     */
    private boolean enabled = true;

    /**
     * 默认启用的编码器 ID（默认：BCryptPasswordEncoder）
     */
    private String encodingId = "bcrypt";

}