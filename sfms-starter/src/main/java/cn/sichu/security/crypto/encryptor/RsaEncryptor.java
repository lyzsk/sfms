package cn.sichu.security.crypto.encryptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;

/**
 * RSA 加/解密处理器
 * <p>
 * 非对称加密算法，由罗纳德·李维斯特（Ron Rivest）、阿迪·沙米尔（Adi Shamir）和伦纳德·阿德曼（Leonard Adleman）于1977年提出，安全性基于大数因子分解问题的困难性。
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class RsaEncryptor implements IEncryptor {

    @Override
    public String encrypt(String plaintext, String password, String publicKey) throws Exception {
        return Base64.encode(SecureUtil.rsa(null, publicKey).encrypt(plaintext, KeyType.PublicKey));
    }

    @Override
    public String decrypt(String ciphertext, String password, String privateKey) throws Exception {
        return new String(SecureUtil.rsa(privateKey, null)
            .decrypt(Base64.decode(ciphertext), KeyType.PrivateKey));
    }
}
