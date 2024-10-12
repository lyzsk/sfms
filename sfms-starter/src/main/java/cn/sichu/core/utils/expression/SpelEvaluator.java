package cn.sichu.core.utils.expression;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Spring EL 表达式解析器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class SpelEvaluator implements Function<Object, Object> {

    private static final ExpressionParser PARSER;
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER;

    static {
        PARSER = new SpelExpressionParser();
        PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    }

    private final Expression expression;
    private String[] parameterNames;

    public SpelEvaluator(String script, Method defineMethod) {
        expression = PARSER.parseExpression(script);
        if (defineMethod.getParameterCount() > 0) {
            parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(defineMethod);
        }
    }

    @Override
    public Object apply(Object rootObject) {
        EvaluationContext context = new StandardEvaluationContext(rootObject);
        ExpressionInvokeContext invokeContext = (ExpressionInvokeContext)rootObject;
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], invokeContext.getArgs()[i]);
            }
        }
        return expression.getValue(context);
    }
}