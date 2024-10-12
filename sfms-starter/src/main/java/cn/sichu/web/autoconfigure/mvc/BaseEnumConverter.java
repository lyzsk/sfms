package cn.sichu.web.autoconfigure.mvc;

import cn.sichu.core.enums.BaseEnum;
import cn.sichu.core.utils.validate.ValidationUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseEnum 参数转换器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class BaseEnumConverter<T extends BaseEnum> implements Converter<String, T> {

    private final Map<String, T> enumMap = new HashMap<>();

    public BaseEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(String.valueOf(e.getValue()), e);
        }
    }

    @Override
    public T convert(String source) {
        T t = enumMap.get(source);
        ValidationUtils.throwIfNull(t, "枚举值非法：{}", source);
        return t;
    }
}
