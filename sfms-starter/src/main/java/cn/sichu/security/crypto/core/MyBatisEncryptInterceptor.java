package cn.sichu.security.crypto.core;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.exception.BaseException;
import cn.sichu.security.crypto.annotation.FieldEncrypt;
import cn.sichu.security.crypto.autoconfigure.CryptoProperties;
import cn.sichu.security.crypto.encryptor.IEncryptor;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字段加密拦截器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class MyBatisEncryptInterceptor extends AbstractMyBatisInterceptor
    implements InnerInterceptor {

    private static final Pattern PARAM_PAIRS_PATTERN =
        Pattern.compile("#\\{ew\\.paramNameValuePairs\\.(" + Constants.WRAPPER_PARAM + "\\d+)\\}");
    private final CryptoProperties properties;

    public MyBatisEncryptInterceptor(CryptoProperties properties) {
        this.properties = properties;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement mappedStatement,
        Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler,
        BoundSql boundSql) {
        if (null == parameterObject) {
            return;
        }
        if (parameterObject instanceof Map parameterMap) {
            this.encryptQueryParameter(parameterMap, mappedStatement);
        }
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement mappedStatement,
        Object parameterObject) {
        if (null == parameterObject) {
            return;
        }
        if (parameterObject instanceof Map parameterMap) {
            // 带别名方法（使用 @Param 注解的场景）
            this.encryptMap(parameterMap, mappedStatement);
        } else {
            // 无别名方法（例如：MP insert 等方法）
            this.encryptEntity(super.getEncryptFields(parameterObject), parameterObject);
        }
    }

    /**
     * 加密 Map 类型数据（使用 @Param 注解的场景）
     *
     * @param parameterMap    参数
     * @param mappedStatement 映射语句
     */
    private void encryptMap(Map<String, Object> parameterMap, MappedStatement mappedStatement) {
        Object parameter;
        // 别名带有 et（针对 MP 的 updateById、update 等方法）
        if (parameterMap.containsKey(Constants.ENTITY) && null != (parameter =
            parameterMap.get(Constants.ENTITY))) {
            this.encryptEntity(super.getEncryptFields(parameter), parameter);
        }
        // 别名带有 ew（针对 MP 的 UpdateWrapper、LambdaUpdateWrapper 等参数）
        if (parameterMap.containsKey(Constants.WRAPPER) && null != (parameter =
            parameterMap.get(Constants.WRAPPER))) {
            this.encryptUpdateWrapper(parameter, mappedStatement);
        }
    }

    /**
     * 加密查询参数（针对 Map 类型参数）
     *
     * @param parameterMap    参数
     * @param mappedStatement 映射语句
     */
    private void encryptQueryParameter(Map<String, Object> parameterMap,
        MappedStatement mappedStatement) {
        Map<String, FieldEncrypt> encryptParameterMap = super.getEncryptParameters(mappedStatement);
        for (Map.Entry<String, Object> parameterEntrySet : parameterMap.entrySet()) {
            String parameterName = parameterEntrySet.getKey();
            Object parameterValue = parameterEntrySet.getValue();
            if (null == parameterValue || ClassUtil.isBasicType(parameterValue.getClass())
                || parameterValue instanceof AbstractWrapper) {
                continue;
            }
            if (parameterValue instanceof String str) {
                FieldEncrypt fieldEncrypt = encryptParameterMap.get(parameterName);
                if (null != fieldEncrypt) {
                    parameterMap.put(parameterName, this.doEncrypt(str, fieldEncrypt));
                }
            } else {
                // 实体参数
                this.encryptEntity(super.getEncryptFields(parameterValue), parameterValue);
            }
        }
    }

    /**
     * 处理 UpdateWrapper 类型参数加密（针对 MP 的 UpdateWrapper、LambdaUpdateWrapper 等参数）
     *
     * @param parameter       Wrapper 参数
     * @param mappedStatement 映射语句
     * @author cary
     * @author wangshaopeng@talkweb.com.cn（<a
     * href="https://blog.csdn.net/tianmaxingkonger/article/details/130986784">基于Mybatis-Plus拦截器实现MySQL数据加解密</a>）
     * @since 2.1.1
     */
    private void encryptUpdateWrapper(Object parameter, MappedStatement mappedStatement) {
        if (parameter instanceof AbstractWrapper updateWrapper) {
            String sqlSet = updateWrapper.getSqlSet();
            if (CharSequenceUtil.isBlank(sqlSet)) {
                return;
            }
            // 将 name=#{ew.paramNameValuePairs.xxx},age=#{ew.paramNameValuePairs.xxx} 切出来
            String[] elArr = sqlSet.split(StringConstants.COMMA);
            Map<String, String> propMap = new HashMap<>(elArr.length);
            Arrays.stream(elArr).forEach(el -> {
                String[] elPart = el.split(StringConstants.EQUALS);
                propMap.put(elPart[0], elPart[1]);
            });
            // 获取加密字段
            Class<?> entityClass = mappedStatement.getParameterMap().getType();
            List<Field> encryptFieldList = super.getEncryptFields(entityClass);
            for (Field field : encryptFieldList) {
                FieldEncrypt fieldEncrypt = field.getAnnotation(FieldEncrypt.class);
                String el = propMap.get(field.getName());
                if (CharSequenceUtil.isBlank(el)) {
                    continue;
                }
                Matcher matcher = PARAM_PAIRS_PATTERN.matcher(el);
                if (matcher.matches()) {
                    String valueKey = matcher.group(1);
                    Object value = updateWrapper.getParamNameValuePairs().get(valueKey);
                    Object ciphertext = this.doEncrypt(value, fieldEncrypt);
                    updateWrapper.getParamNameValuePairs().put(valueKey, ciphertext);
                }
            }
        }
    }

    /**
     * 处理实体加密
     *
     * @param fieldList 加密字段列表
     * @param entity    实体
     */
    private void encryptEntity(List<Field> fieldList, Object entity) {
        for (Field field : fieldList) {
            IEncryptor encryptor = super.getEncryptor(field.getAnnotation(FieldEncrypt.class));
            Object fieldValue = ReflectUtil.getFieldValue(entity, field);
            if (null == fieldValue) {
                continue;
            }
            // 优先获取自定义对称加密算法密钥，获取不到时再获取全局配置
            String password =
                ObjectUtil.defaultIfBlank(field.getAnnotation(FieldEncrypt.class).password(),
                    properties.getPassword());
            String ciphertext;
            try {
                ciphertext =
                    encryptor.encrypt(fieldValue.toString(), password, properties.getPublicKey());
            } catch (Exception e) {
                throw new BaseException(e);
            }
            ReflectUtil.setFieldValue(entity, field, ciphertext);
        }
    }

    /**
     * 处理加密
     *
     * @param parameterValue 参数值
     * @param fieldEncrypt   字段加密注解
     */
    private Object doEncrypt(Object parameterValue, FieldEncrypt fieldEncrypt) {
        if (null == parameterValue) {
            return null;
        }
        IEncryptor encryptor = super.getEncryptor(fieldEncrypt);
        // 优先获取自定义对称加密算法密钥，获取不到时再获取全局配置
        String password =
            ObjectUtil.defaultIfBlank(fieldEncrypt.password(), properties.getPassword());
        try {
            return encryptor.encrypt(parameterValue.toString(), password,
                properties.getPublicKey());
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }
}
