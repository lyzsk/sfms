package cn.sichu.system.service;

import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.system.model.query.MessageQuery;
import cn.sichu.system.model.req.MessageReq;
import cn.sichu.system.model.resp.MessageResp;

import java.util.List;

/**
 * 消息业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IMessageService {

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return cn.sichu.crud.mp.model.resp.PageResp<cn.sichu.system.model.resp.MessageResp> 分页列表信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    PageResp<MessageResp> page(MessageQuery query, PageQuery pageQuery);

    /**
     * 新增
     *
     * @param req        新增信息
     * @param userIdList 接收人列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    void add(MessageReq req, List<Long> userIdList);

    /**
     * 删除
     *
     * @param ids ID 列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    void delete(List<Long> ids);
}