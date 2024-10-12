package cn.sichu.security.crypto.encryptor;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * DES（Data Encryption Standard） 加/解密处理器
 * <p>
 * 一种对称加密算法，使用相同的密钥进行加密和解密。DES 使用 56 位密钥（实际上有 64 位，但有 8 位用于奇偶校验）和一系列置换和替换操作来加密数据。
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class DesEncryptor extends AbstractSymmetricCryptoEncryptor {

    @Override
    protected SymmetricAlgorithm getAlgorithm() {
        return SymmetricAlgorithm.DES;
    }
}
