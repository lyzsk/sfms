package cn.sichu.auth.service;

import cn.sichu.auth.model.query.OnlineUserQuery;
import cn.sichu.auth.model.resp.OnlineUserResp;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.model.dto.LoginUser;

import java.util.Date;
import java.util.List;

/**
 * 在线用户业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IOnlineUserService {
    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return cn.sichu.base.model.resp.PageResp<cn.sichu.auth.model.resp.OnlineUserResp> 分页列表信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    PageResp<OnlineUserResp> page(OnlineUserQuery query, PageQuery pageQuery);

    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return java.util.List<common.model.dto.LoginUser> 列表信息
     * @author sichu huang
     * @date 2024/10/10
     **/
    List<LoginUser> list(OnlineUserQuery query);

    /**
     * 查询 Token 最后活跃时间
     *
     * @param token Token
     * @return java.util.Date 最后活跃时间
     * @author sichu huang
     * @date 2024/10/10
     **/
    Date getLastActiveTime(String token);

    /**
     * 踢出用户
     *
     * @param userId 用户ID
     * @author sichu huang
     * @date 2024/10/10
     **/
    void kickOut(Long userId);
}
