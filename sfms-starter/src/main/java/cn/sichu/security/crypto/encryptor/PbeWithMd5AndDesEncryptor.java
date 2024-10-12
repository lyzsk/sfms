package cn.sichu.security.crypto.encryptor;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * PBEWithMD5AndDES（Password Based Encryption With MD5 And DES） 加/解密处理器
 * <p>
 * 混合加密算法，结合了 MD5 散列算法和 DES（Data Encryption Standard）加密算法
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class PbeWithMd5AndDesEncryptor extends AbstractSymmetricCryptoEncryptor {

    @Override
    protected SymmetricAlgorithm getAlgorithm() {
        return SymmetricAlgorithm.PBEWithMD5AndDES;
    }
}
