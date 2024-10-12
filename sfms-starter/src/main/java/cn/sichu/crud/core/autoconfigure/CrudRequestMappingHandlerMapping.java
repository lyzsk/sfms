package cn.sichu.crud.core.autoconfigure;

import cn.hutool.core.util.ArrayUtil;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.crud.core.annotation.CrudRequestMapping;
import cn.sichu.crud.core.enums.Api;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * CRUD 请求映射器处理器映射器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class CrudRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(@NonNull Method method,
        @NonNull Class<?> handlerType) {
        RequestMappingInfo requestMappingInfo = super.getMappingForMethod(method, handlerType);
        if (null == requestMappingInfo) {
            return null;
        }
        // 如果没有声明 @CrudRequestMapping 注解，直接返回
        if (!handlerType.isAnnotationPresent(CrudRequestMapping.class)) {
            return requestMappingInfo;
        }
        CrudRequestMapping crudRequestMapping =
            handlerType.getDeclaredAnnotation(CrudRequestMapping.class);
        // 过滤 API，如果非本类中定义，且 API 列表中不包含，则忽略
        Api[] apiArr = crudRequestMapping.api();
        Api api = ExceptionUtils.exToNull(() -> Api.valueOf(method.getName().toUpperCase()));
        if (method.getDeclaringClass() != handlerType && !ArrayUtil.containsAny(apiArr, Api.ALL,
            api)) {
            return null;
        }
        // 拼接路径（合并了 @RequestMapping 的部分能力）
        return this.getMappingForMethodWrapper(method, handlerType, crudRequestMapping);
    }

    private RequestMappingInfo getMappingForMethodWrapper(@NonNull Method method,
        @NonNull Class<?> handlerType, CrudRequestMapping crudRequestMapping) {
        RequestMappingInfo info = this.buildRequestMappingInfo(method);
        if (null != info) {
            RequestMappingInfo typeInfo = this.buildRequestMappingInfo(handlerType);
            if (null != typeInfo) {
                info = typeInfo.combine(info);
            }
            String prefix = crudRequestMapping.value();
            if (null != prefix) {
                RequestMappingInfo.BuilderConfiguration options =
                    new RequestMappingInfo.BuilderConfiguration();
                options.setPatternParser(PathPatternParser.defaultInstance);
                info = RequestMappingInfo.paths(prefix).options(options).build().combine(info);
            }
        }
        return info;
    }

    private RequestMappingInfo buildRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping =
            AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition =
            (element instanceof Class<?> clazz ? getCustomTypeCondition(clazz) :
                getCustomMethodCondition((Method)element));
        return (requestMapping != null ? super.createRequestMappingInfo(requestMapping, condition) :
            null);
    }
}
