package cn.sichu.system.service;

import cn.sichu.system.model.resp.MessageUnreadResp;

import java.text.ParseException;
import java.util.List;

/**
 * 消息和用户关联业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IMessageUserService {

    /**
     * 根据用户 ID 查询未读消息数量
     *
     * @param userId   用户 ID
     * @param isDetail 是否查询详情
     * @return cn.sichu.system.model.resp.MessageUnreadResp 未读消息信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    MessageUnreadResp countUnreadMessageByUserId(Long userId, Boolean isDetail);

    /**
     * 新增
     *
     * @param messageId  消息 ID
     * @param userIdList 用户 ID 列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    void add(Long messageId, List<Long> userIdList);

    /**
     * 将消息标记已读
     *
     * @param ids 消息ID（为空则将所有消息标记已读）
     * @author sichu huang
     * @date 2024/10/12
     **/
    void readMessage(List<Long> ids) throws ParseException;

    /**
     * 根据消息 ID 删除
     *
     * @param messageIds 消息 ID 列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    void deleteByMessageIds(List<Long> messageIds);
}