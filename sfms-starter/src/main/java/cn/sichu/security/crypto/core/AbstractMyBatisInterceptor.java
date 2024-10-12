package cn.sichu.security.crypto.core;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.exception.BaseException;
import cn.sichu.security.crypto.annotation.FieldEncrypt;
import cn.sichu.security.crypto.encryptor.IEncryptor;
import cn.sichu.security.crypto.enums.Algorithm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 字段解密拦截器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public abstract class AbstractMyBatisInterceptor {

    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Map<String, FieldEncrypt>> ENCRYPT_PARAM_CACHE =
        new ConcurrentHashMap<>();

    /**
     * 获取所有字符串类型、需要加/解密的、有值字段
     *
     * @param obj 对象
     * @return 字段列表
     */
    protected List<Field> getEncryptFields(Object obj) {
        if (null == obj) {
            return Collections.emptyList();
        }
        return this.getEncryptFields(obj.getClass());
    }

    /**
     * 获取所有字符串类型、需要加/解密的、有值字段
     *
     * @param clazz 类型对象
     * @return 字段列表
     */
    protected List<Field> getEncryptFields(Class<?> clazz) {
        return CLASS_FIELD_CACHE.computeIfAbsent(clazz,
            key -> Arrays.stream(ReflectUtil.getFields(clazz))
                .filter(field -> String.class.equals(field.getType()))
                .filter(field -> null != field.getAnnotation(FieldEncrypt.class)).toList());
    }

    /**
     * 获取字段加/解密处理器
     *
     * @param fieldEncrypt 字段加密注解
     * @return 加/解密处理器
     */
    protected IEncryptor getEncryptor(FieldEncrypt fieldEncrypt) {
        Class<? extends IEncryptor> encryptorClass = fieldEncrypt.encryptor();
        // 使用预定义加/解密处理器
        if (encryptorClass == IEncryptor.class) {
            Algorithm algorithm = fieldEncrypt.value();
            return ReflectUtil.newInstance(algorithm.getEncryptor());
        }
        // 使用自定义加/解密处理器
        return SpringUtil.getBean(encryptorClass);
    }

    /**
     * 获取加密参数
     *
     * @param mappedStatement 映射语句
     * @return 获取加密参数
     */
    protected Map<String, FieldEncrypt> getEncryptParameters(MappedStatement mappedStatement) {
        String mappedStatementId = mappedStatement.getId();
        return ENCRYPT_PARAM_CACHE.computeIfAbsent(mappedStatementId, key -> {
            Method method = this.getMethod(mappedStatementId);
            if (null == method) {
                return Collections.emptyMap();
            }
            Map<String, FieldEncrypt> encryptMap = new HashMap<>();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                FieldEncrypt fieldEncrypt = parameter.getAnnotation(FieldEncrypt.class);
                if (null == fieldEncrypt) {
                    continue;
                }
                String parameterName = this.getParameterName(parameter);
                encryptMap.put(parameterName, fieldEncrypt);
                if (String.class.equals(parameter.getType())) {
                    encryptMap.put("param" + (i + 1), fieldEncrypt);
                }
            }
            return encryptMap;
        });
    }

    /**
     * 获取映射方法
     *
     * @param mappedStatementId 映射语句 ID
     * @return 映射方法
     */
    private Method getMethod(String mappedStatementId) {
        String className = CharSequenceUtil.subBefore(mappedStatementId, StringConstants.DOT, true);
        String methodName = CharSequenceUtil.subAfter(mappedStatementId, StringConstants.DOT, true);
        try {
            Method[] methods = ReflectUtil.getMethods(Class.forName(className));
            return Stream.of(methods).filter(method -> method.getName().equals(methodName))
                .findFirst().orElse(null);
        } catch (ClassNotFoundException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 获取参数名称
     *
     * @param parameter 参数
     * @return 参数名称
     */
    public String getParameterName(Parameter parameter) {
        Param param = parameter.getAnnotation(Param.class);
        return null != param ? param.value() : parameter.getName();
    }
}