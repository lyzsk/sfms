package cn.sichu.base;

import com.alibaba.excel.event.AnalysisEventListener;

/**
 * 自定义解析结果监听器
 *
 * @author sichu huang
 * @since 2024/10/16 22:03
 */
public abstract class BaseAnalysisEventListener<T> extends AnalysisEventListener<T> {
    private String msg;

    public abstract String getMsg();
}
