package cn.sichu.system.service;

import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.DictItemDO;
import cn.sichu.system.model.query.DictItemQuery;
import cn.sichu.system.model.req.DictItemReq;
import cn.sichu.system.model.resp.DictItemResp;

import java.util.List;

/**
 * 字典项业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IDictItemService
    extends BaseService<DictItemResp, DictItemResp, DictItemQuery, DictItemReq>,
    IService<DictItemDO> {
    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return java.util.List<cn.sichu.crud.core.resp.LabelValueResp> 字典项列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<LabelValueResp> listByDictCode(String dictCode);

    /**
     * 根据字典ID列表 删除
     *
     * @param dictIds 字典ID列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    void deleteByDictIds(List<Long> dictIds);

    /**
     * 查询枚举字典名称列表
     *
     * @return java.util.List<java.lang.String> 枚举字典名称列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<String> listEnumDictNames();
}
