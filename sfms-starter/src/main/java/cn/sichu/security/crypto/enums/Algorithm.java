package cn.sichu.security.crypto.enums;

import cn.sichu.security.crypto.encryptor.*;
import lombok.Getter;

/**
 * 加密/解密算法枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
public enum Algorithm {

    /**
     * AES
     */
    AES(AesEncryptor.class),

    /**
     * DES
     */
    DES(DesEncryptor.class),

    /**
     * PBEWithMD5AndDES
     */
    PBEWithMD5AndDES(PbeWithMd5AndDesEncryptor.class),

    /**
     * RSA
     */
    RSA(RsaEncryptor.class),

    /**
     * Base64
     */
    BASE64(Base64Encryptor.class),
    ;

    /**
     * 加密/解密处理器
     */
    private final Class<? extends IEncryptor> encryptor;

    Algorithm(Class<? extends IEncryptor> encryptor) {
        this.encryptor = encryptor;
    }

}
