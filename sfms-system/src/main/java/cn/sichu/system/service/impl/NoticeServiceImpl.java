package cn.sichu.system.service.impl;

import cn.sichu.crud.mp.service.impl.BaseServiceImpl;
import cn.sichu.system.mapper.NoticeMapper;
import cn.sichu.system.model.entity.NoticeDO;
import cn.sichu.system.model.query.NoticeQuery;
import cn.sichu.system.model.req.NoticeReq;
import cn.sichu.system.model.resp.DashboardNoticeResp;
import cn.sichu.system.model.resp.NoticeDetailResp;
import cn.sichu.system.model.resp.NoticeResp;
import cn.sichu.system.service.INoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告业务实现
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends
    BaseServiceImpl<NoticeMapper, NoticeDO, NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq>
    implements INoticeService {

    @Override
    public List<DashboardNoticeResp> listDashboard() {
        return baseMapper.selectDashboardList();
    }
}