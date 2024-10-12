package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.NoticeDO;
import cn.sichu.system.model.query.NoticeQuery;
import cn.sichu.system.model.req.NoticeReq;
import cn.sichu.system.model.resp.DashboardNoticeResp;
import cn.sichu.system.model.resp.NoticeDetailResp;
import cn.sichu.system.model.resp.NoticeResp;

import java.util.List;

/**
 * 公告业务接口
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface INoticeService
    extends BaseService<NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq>, IService<NoticeDO> {

    /**
     * 查询仪表盘公告列表
     *
     * @return java.util.List<cn.sichu.system.model.resp.DashboardNoticeResp> 仪表盘公告列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardNoticeResp> listDashboard();
}