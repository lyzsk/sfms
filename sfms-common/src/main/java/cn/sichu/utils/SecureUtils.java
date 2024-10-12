package cn.sichu.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.config.properties.RsaProperties;
import cn.sichu.core.exception.BusinessException;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.security.crypto.autoconfigure.CryptoProperties;
import cn.sichu.security.crypto.encryptor.AesEncryptor;
import cn.sichu.security.crypto.encryptor.IEncryptor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 加密/解密工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class SecureUtils {

    private SecureUtils() {
    }

    /**
     * 私钥解密
     *
     * @param data 要解密的内容（Base64 加密过）
     * @return java.lang.String 解密后的内容
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static String decryptByRsaPrivateKey(String data) {
        String privateKey = RsaProperties.PRIVATE_KEY;
        ValidationUtils.throwIfBlank(privateKey, "请配置 RSA 私钥");
        return decryptByRsaPrivateKey(data, privateKey);
    }

    /**
     * 私钥解密
     *
     * @param data       要解密的内容（Base64 加密过）
     * @param privateKey 私钥
     * @return java.lang.String 解密后的内容
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static String decryptByRsaPrivateKey(String data, String privateKey) {
        return new String(
            SecureUtil.rsa(privateKey, null).decrypt(Base64.decode(data), KeyType.PrivateKey));
    }

    /**
     * 对普通加密字段列表进行AES加密，优化starter加密模块后优化这个方法
     *
     * @param values 待加密内容
     * @return java.util.List<java.lang.String> 加密后内容
     * @author sichu huang
     * @date 2024/10/12
     **/
    public static List<String> encryptFieldByAes(List<String> values) {
        IEncryptor encryptor = new AesEncryptor();
        CryptoProperties properties = SpringUtil.getBean(CryptoProperties.class);
        return values.stream().map(value -> {
            try {
                return encryptor.encrypt(value, properties.getPassword(),
                    properties.getPublicKey());
            } catch (Exception e) {
                throw new BusinessException("字段加密异常");
            }
        }).collect(Collectors.toList());
    }
}
