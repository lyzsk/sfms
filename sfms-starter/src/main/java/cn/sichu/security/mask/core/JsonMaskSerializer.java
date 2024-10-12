package cn.sichu.security.mask.core;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.security.mask.annotation.JsonMask;
import cn.sichu.security.mask.strategy.IMaskStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * JSON 脱敏序列化器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class JsonMaskSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private JsonMask jsonMask;

    public JsonMaskSerializer(JsonMask jsonMask) {
        this.jsonMask = jsonMask;
    }

    public JsonMaskSerializer() {
    }

    @Override
    public void serialize(String str, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
        if (CharSequenceUtil.isBlank(str)) {
            jsonGenerator.writeString(StringConstants.EMPTY);
            return;
        }
        // 使用自定义脱敏策略
        Class<? extends IMaskStrategy> strategyClass = jsonMask.strategy();
        IMaskStrategy maskStrategy =
            strategyClass != IMaskStrategy.class ? SpringUtil.getBean(strategyClass) :
                jsonMask.value();
        jsonGenerator.writeString(
            maskStrategy.mask(str, jsonMask.character(), jsonMask.left(), jsonMask.right()));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider,
        BeanProperty beanProperty) throws JsonMappingException {
        if (null == beanProperty) {
            return serializerProvider.findNullValueSerializer(null);
        }
        if (!Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        JsonMask jsonMaskAnnotation =
            ObjectUtil.defaultIfNull(beanProperty.getAnnotation(JsonMask.class),
                beanProperty.getContextAnnotation(JsonMask.class));
        if (null == jsonMaskAnnotation) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return new JsonMaskSerializer(jsonMaskAnnotation);
    }
}
