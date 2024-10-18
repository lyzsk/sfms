package cn.sichu.system.service;

import cn.sichu.system.model.entity.Notice;
import cn.sichu.system.model.form.NoticeForm;
import cn.sichu.system.model.query.NoticePageQuery;
import cn.sichu.system.model.vo.NoticeDetailVO;
import cn.sichu.system.model.vo.NoticePageVO;
import cn.sichu.system.model.vo.UserNoticePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 通知公告服务类
 *
 * @author sichu huang
 * @since 2024/10/16 23:57:12
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 通知公告分页列表
     *
     * @param queryParams queryParams
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.model.vo.NoticePageVO> 通知公告分页列表
     * @author sichu huang
     * @since 2024/10/16 23:57:43
     */
    IPage<NoticePageVO> getNoticePage(NoticePageQuery queryParams);

    /**
     * 获取通知公告表单数据
     *
     * @param id 通知公告ID
     * @return cn.sichu.model.form.NoticeForm 通知公告表单对象
     * @author sichu huang
     * @since 2024/10/16 23:57:58
     */
    NoticeForm getNoticeFormData(Long id);

    /**
     * 新增通知公告
     *
     * @param formData 通知公告表单对象
     * @return boolean 是否新增成功
     * @author sichu huang
     * @since 2024/10/16 23:58:11
     */
    boolean saveNotice(NoticeForm formData);

    /**
     * 修改通知公告
     *
     * @param id       通知公告ID
     * @param formData 通知公告表单对象
     * @return boolean 是否修改成功
     * @author sichu huang
     * @since 2024/10/16 23:58:23
     */
    boolean updateNotice(Long id, NoticeForm formData);

    /**
     * 删除通知公告
     *
     * @param ids 通知公告ID，多个以英文逗号(,)分割
     * @return boolean 是否删除成功
     * @author sichu huang
     * @since 2024/10/16 23:58:34
     */
    boolean deleteNotices(String ids);

    /**
     * 发布通知公告
     *
     * @param id 通知公告ID
     * @return boolean 是否发布成功
     * @author sichu huang
     * @since 2024/10/16 23:58:47
     */
    boolean publishNotice(Long id);

    /**
     * 撤回通知公告
     *
     * @param id 通知公告ID
     * @return boolean 是否撤回成功
     * @author sichu huang
     * @since 2024/10/16 23:58:58
     */
    boolean revokeNotice(Long id);

    /**
     * 阅读获取通知公告详情
     *
     * @param id 通知公告ID
     * @return cn.sichu.model.vo.NoticeDetailVO 通知公告详情
     * @author sichu huang
     * @since 2024/10/16 23:59:10
     */
    NoticeDetailVO getNoticeDetail(Long id);

    /**
     * 获取我的通知公告分页列表
     *
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.model.vo.UserNoticePageVO> 通知公告分页列表
     * @author sichu huang
     * @since 2024/10/16 23:59:21
     */
    IPage<UserNoticePageVO> getMyNoticePage(NoticePageQuery queryParams);
}
