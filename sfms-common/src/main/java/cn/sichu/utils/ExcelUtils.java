package cn.sichu.utils;

import cn.sichu.base.BaseAnalysisEventListener;
import com.alibaba.excel.EasyExcel;

import java.io.InputStream;

/**
 * @author sichu huang
 * @since 2024/10/16 22:03
 */
public class ExcelUtils {
    public static <T> String importExcel(InputStream is, Class clazz,
        BaseAnalysisEventListener<T> listener) {
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getMsg();
    }
}
