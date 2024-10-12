package cn.sichu.core.utils.expression;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 表达式解析器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ExpressionEvaluator implements Function<Object, Object> {

    private final Function<Object, Object> evaluator;

    public ExpressionEvaluator(String script, Method defineMethod) {
        this.evaluator = new SpelEvaluator(script, defineMethod);
    }

    @Override
    public Object apply(Object rootObject) {
        return evaluator.apply(rootObject);
    }

    Function<Object, Object> getEvaluator() {
        return evaluator;
    }
}
