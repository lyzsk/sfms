package cn.sichu.web.annotation;

import cn.sichu.web.autoconfigure.response.GlobalResponseAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({GlobalResponseAutoConfiguration.class})
public @interface EnableGlobalResponse {
}
