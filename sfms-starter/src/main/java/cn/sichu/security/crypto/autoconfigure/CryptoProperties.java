package cn.sichu.security.crypto.autoconfigure;

import cn.sichu.core.constant.PropertiesConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加/解密配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties(PropertiesConstants.SECURITY_CRYPTO)
public class CryptoProperties {

    /**
     * 是否启用加/解密配置
     */
    private boolean enabled = true;

    /**
     * 对称加密算法密钥
     */
    private String password;

    /**
     * 非对称加密算法公钥
     */
    private String publicKey;

    /**
     * 非对称加密算法私钥
     */
    private String privateKey;

}