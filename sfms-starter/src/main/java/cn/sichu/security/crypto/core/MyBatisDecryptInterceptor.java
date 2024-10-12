package cn.sichu.security.crypto.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.sichu.security.crypto.annotation.FieldEncrypt;
import cn.sichu.security.crypto.autoconfigure.CryptoProperties;
import cn.sichu.security.crypto.encryptor.IEncryptor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.type.SimpleTypeRegistry;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;

/**
 * 字段解密拦截器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets",
    args = {Statement.class})})
public class MyBatisDecryptInterceptor extends AbstractMyBatisInterceptor implements Interceptor {

    private CryptoProperties properties;

    public MyBatisDecryptInterceptor(CryptoProperties properties) {
        this.properties = properties;
    }

    public MyBatisDecryptInterceptor() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object obj = invocation.proceed();
        if (null == obj || !(invocation.getTarget() instanceof ResultSetHandler)) {
            return obj;
        }
        List<?> resultList = (List<?>)obj;
        for (Object result : resultList) {
            // String、Integer、Long 等简单类型对象无需处理
            if (SimpleTypeRegistry.isSimpleType(result.getClass())) {
                continue;
            }
            // 获取所有字符串类型、需要解密的、有值字段
            List<Field> fieldList = super.getEncryptFields(result);
            // 解密处理
            for (Field field : fieldList) {
                IEncryptor encryptor = super.getEncryptor(field.getAnnotation(FieldEncrypt.class));
                Object fieldValue = ReflectUtil.getFieldValue(result, field);
                if (null == fieldValue) {
                    continue;
                }
                // 优先获取自定义对称加密算法密钥，获取不到时再获取全局配置
                String password =
                    ObjectUtil.defaultIfBlank(field.getAnnotation(FieldEncrypt.class).password(),
                        properties.getPassword());
                String ciphertext =
                    encryptor.decrypt(fieldValue.toString(), password, properties.getPrivateKey());
                ReflectUtil.setFieldValue(result, field, ciphertext);
            }
        }
        return resultList;
    }
}