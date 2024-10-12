package cn.sichu.security.crypto.encryptor;

/**
 * 加/解密接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IEncryptor {

    /**
     * 加密
     *
     * @param plaintext 明文
     * @param password  对称加密算法密钥
     * @param publicKey 非对称加密算法公钥
     * @return 加密后的文本
     * @throws Exception /
     */
    String encrypt(String plaintext, String password, String publicKey) throws Exception;

    /**
     * 解密
     *
     * @param ciphertext 密文
     * @param password   对称加密算法密钥
     * @param privateKey 非对称加密算法私钥
     * @return 解密后的文本
     * @throws Exception /
     */
    String decrypt(String ciphertext, String password, String privateKey) throws Exception;
}
