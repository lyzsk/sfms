package cn.sichu.security.crypto.encryptor;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * AES（Advanced Encryption Standard） 加/解密处理器
 * <p>
 * 美国国家标准与技术研究院(NIST)采纳的对称加密算法标准，提供128位、192位和256位三种密钥长度，以高效和安全性著称。
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class AesEncryptor extends AbstractSymmetricCryptoEncryptor {

    @Override
    protected SymmetricAlgorithm getAlgorithm() {
        return SymmetricAlgorithm.AES;
    }
}
