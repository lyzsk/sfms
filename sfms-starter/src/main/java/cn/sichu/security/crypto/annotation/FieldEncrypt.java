package cn.sichu.security.crypto.annotation;

import cn.sichu.security.crypto.encryptor.IEncryptor;
import cn.sichu.security.crypto.enums.Algorithm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段加/解密注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldEncrypt {

    /**
     * 加密/解密算法
     */
    Algorithm value() default Algorithm.AES;

    /**
     * 加密/解密处理器
     * <p>
     * 优先级高于加密/解密算法
     * </p>
     */
    Class<? extends IEncryptor> encryptor() default IEncryptor.class;

    /**
     * 对称加密算法密钥
     */
    String password() default "";
}