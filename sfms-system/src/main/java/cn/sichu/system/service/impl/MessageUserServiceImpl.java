package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.sichu.core.utils.DateUtils;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.system.enums.MessageTypeEnum;
import cn.sichu.system.mapper.MessageUserMapper;
import cn.sichu.system.model.entity.MessageUserDO;
import cn.sichu.system.model.resp.MessageTypeUnreadResp;
import cn.sichu.system.model.resp.MessageUnreadResp;
import cn.sichu.system.service.IMessageUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息和用户关联业务实现
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class MessageUserServiceImpl implements IMessageUserService {

    private final MessageUserMapper baseMapper;

    @Override
    public MessageUnreadResp countUnreadMessageByUserId(Long userId, Boolean isDetail) {
        MessageUnreadResp result = new MessageUnreadResp();
        Long total = 0L;
        if (Boolean.TRUE.equals(isDetail)) {
            List<MessageTypeUnreadResp> detailList = new ArrayList<>();
            for (MessageTypeEnum messageType : MessageTypeEnum.values()) {
                MessageTypeUnreadResp resp = new MessageTypeUnreadResp();
                resp.setType(messageType);
                Long count =
                    baseMapper.selectUnreadCountByUserIdAndType(userId, messageType.getValue());
                resp.setCount(count);
                detailList.add(resp);
                total += count;
            }
            result.setDetails(detailList);
        } else {
            total = baseMapper.selectUnreadCountByUserIdAndType(userId, null);
        }
        result.setTotal(total);
        return result;
    }

    @Override
    public void add(Long messageId, List<Long> userIdList) {
        CheckUtils.throwIfEmpty(userIdList, "消息接收人不能为空");
        List<MessageUserDO> messageUserList = userIdList.stream().map(userId -> {
            MessageUserDO messageUser = new MessageUserDO();
            messageUser.setUserId(userId);
            messageUser.setMessageId(messageId);
            messageUser.setIsRead(false);
            return messageUser;
        }).toList();
        baseMapper.insertBatch(messageUserList);
    }

    @Override
    public void readMessage(List<Long> ids) throws ParseException {
        baseMapper.lambdaUpdate().set(MessageUserDO::getIsRead, true)
            .set(MessageUserDO::getReadTime, DateUtils.parseMillisecond(new Date()))
            .eq(MessageUserDO::getIsRead, false)
            .in(CollUtil.isNotEmpty(ids), MessageUserDO::getMessageId, ids).update();
    }

    @Override
    public void deleteByMessageIds(List<Long> messageIds) {
        baseMapper.lambdaUpdate().in(MessageUserDO::getMessageId, messageIds).remove();
    }
}