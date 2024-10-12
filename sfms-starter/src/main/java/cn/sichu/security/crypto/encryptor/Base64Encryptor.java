package cn.sichu.security.crypto.encryptor;

import cn.hutool.core.codec.Base64;

/**
 * Base64 加/解密处理器
 * <p>
 * 一种用于编码二进制数据到文本格式的算法，常用于邮件附件、网页传输等场合，但它不是一种加密算法，只提供数据的编码和解码，不保证数据的安全性。
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class Base64Encryptor implements IEncryptor {

    @Override
    public String encrypt(String plaintext, String password, String publicKey) throws Exception {
        return Base64.encode(plaintext);
    }

    @Override
    public String decrypt(String ciphertext, String password, String privateKey) throws Exception {
        return Base64.decodeStr(ciphertext);
    }
}
