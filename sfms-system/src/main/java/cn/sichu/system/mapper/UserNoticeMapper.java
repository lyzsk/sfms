package cn.sichu.system.mapper;

import cn.sichu.system.model.entity.UserNotice;
import cn.sichu.system.model.query.NoticePageQuery;
import cn.sichu.system.model.vo.NoticePageVO;
import cn.sichu.system.model.vo.UserNoticePageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author sichu huang
 * @since 2024/10/16 23:26
 */
@Mapper
public interface UserNoticeMapper extends BaseMapper<UserNotice> {

    /**
     * 分页获取我的通知公告
     *
     * @param page        分页对象
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.model.vo.UserNoticePageVO> 通知公告分页列表
     * @author sichu huang
     * @since 2024/10/16 23:26:52
     */
    IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page,
        @Param("queryParams") NoticePageQuery queryParams);

}
