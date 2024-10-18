package cn.sichu.system.service;

import cn.sichu.system.model.entity.UserNotice;
import cn.sichu.system.model.query.NoticePageQuery;
import cn.sichu.system.model.vo.NoticePageVO;
import cn.sichu.system.model.vo.UserNoticePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户公告状态服务类
 *
 * @author sichu huang
 * @since 2024/10/17 00:03:52
 */
public interface UserNoticeService extends IService<UserNotice> {

    /**
     * 全部标记为已读
     *
     * @return boolean 是否成功
     * @author sichu huang
     * @since 2024/10/17 00:04:11
     */
    boolean readAll();

    /**
     * 分页获取我的通知公告
     *
     * @param page        分页对象
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.model.vo.UserNoticePageVO> 我的通知公告分页列表
     * @author sichu huang
     * @since 2024/10/17 00:04:21
     */
    IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams);
}
