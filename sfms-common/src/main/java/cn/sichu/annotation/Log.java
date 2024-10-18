package cn.sichu.annotation;

import cn.sichu.enums.LogModuleEnum;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author sichu huang
 * @since 2024/10/16 22:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Log {
    String value() default "";

    LogModuleEnum module();
}
