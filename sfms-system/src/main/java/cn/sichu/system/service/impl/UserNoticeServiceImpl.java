package cn.sichu.system.service.impl;

import cn.sichu.system.core.utils.SecurityUtils;
import cn.sichu.system.mapper.UserNoticeMapper;
import cn.sichu.system.model.entity.UserNotice;
import cn.sichu.system.model.query.NoticePageQuery;
import cn.sichu.system.model.vo.NoticePageVO;
import cn.sichu.system.model.vo.UserNoticePageVO;
import cn.sichu.system.service.UserNoticeService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author sichu huang
 * @since 2024/10/17 16:21
 */
@Service
@RequiredArgsConstructor
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice>
    implements UserNoticeService {

    /**
     * 全部标记为已读
     *
     * @return 是否成功
     */
    @Override
    public boolean readAll() {
        Long userId = SecurityUtils.getUserId();
        return this.update(new LambdaUpdateWrapper<UserNotice>().eq(UserNotice::getUserId, userId)
            .eq(UserNotice::getIsRead, 0).set(UserNotice::getIsRead, 1));
    }

    @Override
    public IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page,
        NoticePageQuery queryParams) {
        return this.getBaseMapper()
            .getMyNoticePage(new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams);
    }
}
