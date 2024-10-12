package cn.sichu.core.utils.expression;

import java.lang.reflect.Method;

/**
 * 表达式上下文
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ExpressionInvokeContext {

    /**
     * 目标方法
     */
    private Method method;

    /**
     * 方法参数
     */
    private Object[] args;

    /**
     * 目标对象
     */
    private Object target;

    public ExpressionInvokeContext(Method method, Object[] args, Object target) {
        this.method = method;
        this.args = args;
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
