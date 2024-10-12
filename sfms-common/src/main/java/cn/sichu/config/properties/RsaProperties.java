package cn.sichu.config.properties;

import cn.hutool.extra.spring.SpringUtil;

/**
 * RSA 配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class RsaProperties {

    /**
     * 私钥
     **/
    public static final String PRIVATE_KEY;
    /**
     * 公钥
     **/
    public static final String PUBLIC_KEY;

    static {
        PRIVATE_KEY = SpringUtil.getProperty("sfms.security.crypto.private-key");
        PUBLIC_KEY = SpringUtil.getProperty("sfms.security.crypto.public-key");
    }

    private RsaProperties() {
    }
}
