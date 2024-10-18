package cn.sichu.system.mapper;

import cn.sichu.system.model.bo.NoticeBO;
import cn.sichu.system.model.entity.Notice;
import cn.sichu.system.model.query.NoticePageQuery;
import cn.sichu.system.model.vo.NoticePageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author sichu huang
 * @since 2024/10/16 23:22
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 获取通知公告分页数据
     *
     * @param page        分页对象
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.bo.NoticeBO> 通知公告分页数据
     * @author sichu huang
     * @since 2024/10/16 23:22:58
     */
    Page<NoticeBO> getNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams);

    /**
     * 获取阅读时通知公告详情
     *
     * @param id 通知公告ID
     * @return cn.sichu.model.bo.NoticeBO 通知公告详情
     * @author sichu huang
     * @since 2024/10/16 23:23:15
     */
    NoticeBO getNoticeDetail(@Param("id") Long id);
}
