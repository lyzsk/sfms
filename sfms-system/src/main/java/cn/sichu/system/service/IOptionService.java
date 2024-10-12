package cn.sichu.system.service;

import cn.sichu.system.model.query.OptionQuery;
import cn.sichu.system.model.req.OptionReq;
import cn.sichu.system.model.req.OptionResetValueReq;
import cn.sichu.system.model.resp.OptionResp;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 参数业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IOptionService {
    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return java.util.List<cn.sichu.system.model.resp.OptionResp> 列表信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    List<OptionResp> list(OptionQuery query);

    /**
     * 根据类别查询
     *
     * @param category 类别
     * @return java.util.Map<java.lang.String, java.lang.String> 参数信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    Map<String, String> getByCategory(String category);

    /**
     * 修改参数
     *
     * @param options 参数列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    void update(List<OptionReq> options);

    /**
     * 重置参数
     *
     * @param req 重置信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    void resetValue(OptionResetValueReq req);

    /**
     * 根据编码查询参数值
     *
     * @param code 编码
     * @return int 参数值（自动转换为 int 类型）
     * @author sichu huang
     * @date 2024/10/10
     **/
    int getValueByCode2Int(String code);

    /**
     * 根据编码查询参数值
     *
     * @param code   编码
     * @param mapper 转换方法 e.g.：value -> Integer.parseInt(value)
     * @return T 参数值
     * @author sichu huang
     * @date 2024/10/10
     **/
    <T> T getValueByCode(String code, Function<String, T> mapper);
}
