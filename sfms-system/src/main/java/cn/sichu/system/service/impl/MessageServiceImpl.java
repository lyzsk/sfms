package cn.sichu.system.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.data.mp.utils.QueryWrapperHelper;
import cn.sichu.system.mapper.MessageMapper;
import cn.sichu.system.model.entity.MessageDO;
import cn.sichu.system.model.query.MessageQuery;
import cn.sichu.system.model.req.MessageReq;
import cn.sichu.system.model.resp.MessageResp;
import cn.sichu.system.service.IMessageService;
import cn.sichu.system.service.IMessageUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息业务实现
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

    private final MessageMapper baseMapper;
    private final IMessageUserService messageUserService;

    @Override
    @AutoOperate(type = MessageResp.class, on = "list")
    public PageResp<MessageResp> page(MessageQuery query, PageQuery pageQuery) {
        QueryWrapper<MessageDO> queryWrapper = QueryWrapperHelper.build(query, pageQuery.getSort());
        queryWrapper.apply(null != query.getUserId(), "t2.user_id={0}", query.getUserId())
            .apply(null != query.getIsRead(), "t2.is_read={0}", query.getIsRead());
        IPage<MessageResp> page =
            baseMapper.selectPageByUserId(new Page<>(pageQuery.getPage(), pageQuery.getSize()),
                queryWrapper);
        return PageResp.build(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MessageReq req, List<Long> userIdList) {
        CheckUtils.throwIf(() -> CollUtil.isEmpty(userIdList), "消息接收人不能为空");
        MessageDO message = BeanUtil.copyProperties(req, MessageDO.class);
        baseMapper.insert(message);
        messageUserService.add(message.getId(), userIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
        messageUserService.deleteByMessageIds(ids);
    }
}