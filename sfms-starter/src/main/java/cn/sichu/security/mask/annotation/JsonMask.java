package cn.sichu.security.mask.annotation;

import cn.sichu.core.constant.StringConstants;
import cn.sichu.security.mask.core.JsonMaskSerializer;
import cn.sichu.security.mask.enums.MaskType;
import cn.sichu.security.mask.strategy.IMaskStrategy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON 脱敏注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = JsonMaskSerializer.class)
public @interface JsonMask {

    /**
     * 脱敏类型
     */
    MaskType value() default MaskType.CUSTOM;

    /**
     * 脱敏策略
     * <p>
     * 优先级高于脱敏类型
     * </p>
     */
    Class<? extends IMaskStrategy> strategy() default IMaskStrategy.class;

    /**
     * 左侧保留位数
     * <p>
     * 仅在脱敏类型为 {@code DesensitizedType.CUSTOM } 时使用
     * </p>
     */
    int left() default 0;

    /**
     * 右侧保留位数
     * <p>
     * 仅在脱敏类型为 {@code DesensitizedType.CUSTOM } 时使用
     * </p>
     */
    int right() default 0;

    /**
     * 脱敏符号（默认：*）
     */
    char character() default StringConstants.C_ASTERISK;
}