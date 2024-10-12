package cn.sichu.crud.core.annotation;

import cn.sichu.crud.core.autoconfigure.CrudRestControllerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * CRUD REST Controller 启用注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CrudRestControllerAutoConfiguration.class})
public @interface EnableCrudRestController {
}
