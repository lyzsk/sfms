package cn.sichu.security.crypto.encryptor;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.sichu.core.constant.StringConstants;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对称加/解密处理器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public abstract class AbstractSymmetricCryptoEncryptor implements IEncryptor {

    private static final Map<String, SymmetricCrypto> CACHE = new ConcurrentHashMap<>();

    @Override
    public String encrypt(String plaintext, String password, String publicKey) throws Exception {
        if (CharSequenceUtil.isBlank(plaintext)) {
            return plaintext;
        }
        return this.getCrypto(password).encryptHex(plaintext);
    }

    @Override
    public String decrypt(String ciphertext, String password, String privateKey) throws Exception {
        if (CharSequenceUtil.isBlank(ciphertext)) {
            return ciphertext;
        }
        return this.getCrypto(password).decryptStr(ciphertext);
    }

    /**
     * 获取对称加密算法
     *
     * @param password 密钥
     * @return 对称加密算法
     */
    protected SymmetricCrypto getCrypto(String password) {
        SymmetricAlgorithm algorithm = this.getAlgorithm();
        String key = algorithm + StringConstants.UNDERLINE + password;
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }
        SymmetricCrypto symmetricCrypto =
            new SymmetricCrypto(algorithm, password.getBytes(StandardCharsets.UTF_8));
        CACHE.put(key, symmetricCrypto);
        return symmetricCrypto;
    }

    /**
     * 获取对称加密算法类型
     *
     * @return 对称加密算法类型
     */
    protected abstract SymmetricAlgorithm getAlgorithm();
}
