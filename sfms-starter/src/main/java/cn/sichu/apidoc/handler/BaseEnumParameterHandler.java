package cn.sichu.apidoc.handler;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.apidoc.utils.DocUtils;
import cn.sichu.core.enums.BaseEnum;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义 BaseEnum 枚举参数处理器
 * <p>
 * 针对实现了 BaseEnum 的枚举，优化其枚举值和描述展示
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class BaseEnumParameterHandler implements ParameterCustomizer, PropertyCustomizer {

    @Override
    public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        // 判断是否为 BaseEnum 的子类型
        if (!ClassUtil.isAssignable(BaseEnum.class, parameterType)) {
            return parameterModel;
        }
        String description = parameterModel.getDescription();
        if (StrUtil.contains(description, "color:red")) {
            return parameterModel;
        }
        // 自定义枚举描述并封装参数配置
        configureSchema(parameterModel.getSchema(), parameterType);
        parameterModel.setDescription(appendEnumDescription(description, parameterType));
        return parameterModel;
    }

    @Override
    public Schema customize(Schema schema, AnnotatedType type) {
        Class<?> rawClass = resolveRawClass(type.getType());
        // 判断是否为 BaseEnum 的子类型
        if (!ClassUtil.isAssignable(BaseEnum.class, rawClass)) {
            return schema;
        }
        // 自定义参数描述并封装参数配置
        configureSchema(schema, rawClass);
        schema.setDescription(appendEnumDescription(schema.getDescription(), rawClass));
        return schema;
    }

    /**
     * 封装 Schema 配置
     *
     * @param schema    Schema
     * @param enumClass 枚举类型
     */
    private void configureSchema(Schema schema, Class<?> enumClass) {
        BaseEnum[] enums = (BaseEnum[])enumClass.getEnumConstants();
        List<String> valueList = Arrays.stream(enums).map(e -> e.getValue().toString()).toList();
        schema.setEnum(valueList);
        String enumValueType = DocUtils.getEnumValueTypeAsString(enumClass);
        schema.setType(enumValueType);
        schema.setFormat(DocUtils.resolveFormat(enumValueType));
    }

    /**
     * 追加枚举描述
     *
     * @param originalDescription 原始描述
     * @param enumClass           枚举类型
     * @return 追加后的描述字符串
     */
    private String appendEnumDescription(String originalDescription, Class<?> enumClass) {
        return originalDescription + "<span style='color:red'>" + DocUtils.getDescMap(enumClass)
            + "</span>";
    }

    /**
     * 解析原始类
     *
     * @param type 类型
     * @return 原始类的 Class 对象
     */
    private Class<?> resolveRawClass(Type type) {
        if (type instanceof SimpleType simpleType) {
            return simpleType.getRawClass();
        } else if (type instanceof CollectionType collectionType) {
            return collectionType.getContentType().getRawClass();
        } else {
            return Object.class;
        }
    }
}
