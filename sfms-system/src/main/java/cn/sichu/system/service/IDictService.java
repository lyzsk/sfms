package cn.sichu.system.service;

import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.DictDO;
import cn.sichu.system.model.query.DictQuery;
import cn.sichu.system.model.req.DictReq;
import cn.sichu.system.model.resp.DictResp;

import java.util.List;

/**
 * 字典业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IDictService
    extends BaseService<DictResp, DictResp, DictQuery, DictReq>, IService<DictDO> {

    /**
     * 查询枚举字典
     *
     * @return java.util.List<cn.sichu.crud.core.resp.LabelValueResp> 枚举字典列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<LabelValueResp> listEnumDict();
}