package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.StorageDO;
import cn.sichu.system.model.query.StorageQuery;
import cn.sichu.system.model.req.StorageReq;
import cn.sichu.system.model.resp.StorageResp;

/**
 * 存储业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IStorageService
    extends BaseService<StorageResp, StorageResp, StorageQuery, StorageReq>, IService<StorageDO> {

    /**
     * 查询默认存储
     *
     * @return cn.sichu.system.model.entity.StorageDO 存储信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    StorageDO getDefaultStorage();

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return cn.sichu.system.model.entity.StorageDO 存储信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    StorageDO getByCode(String code);

    /**
     * 加载存储
     *
     * @param req 存储信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    void load(StorageReq req);

    /**
     * 卸载存储
     *
     * @param req 存储信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    void unload(StorageReq req);
}
