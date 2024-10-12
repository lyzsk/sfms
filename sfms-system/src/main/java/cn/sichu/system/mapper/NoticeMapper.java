package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.NoticeDO;
import cn.sichu.system.model.resp.DashboardNoticeResp;

import java.util.List;

/**
 * 公告 Mapper
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface NoticeMapper extends BaseMapper<NoticeDO> {

    /**
     * 查询仪表盘公告列表
     *
     * @return java.util.List<cn.sichu.system.model.resp.DashboardNoticeResp> 仪表盘公告列表
     * @author sichu huang
     * @date 2024/10/12
     **/
    List<DashboardNoticeResp> selectDashboardList();
}