package cn.sichu.data.core.function;

import java.io.Serializable;

/**
 * SQL 函数接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface ISqlFunction {

    /**
     * find_in_set 函数
     *
     * @param value 值
     * @param set   集合
     * @return 函数实现
     */
    String findInSet(Serializable value, String set);
}
