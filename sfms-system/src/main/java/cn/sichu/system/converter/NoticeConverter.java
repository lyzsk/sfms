package cn.sichu.system.converter;

import cn.sichu.system.model.bo.NoticeBO;
import cn.sichu.system.model.entity.Notice;
import cn.sichu.system.model.form.NoticeForm;
import cn.sichu.system.model.vo.NoticeDetailVO;
import cn.sichu.system.model.vo.NoticePageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 通知公告对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:32:22
 */
@Mapper(componentModel = "spring")
public interface NoticeConverter {

    @Mappings({@Mapping(target = "targetUserIds",
        expression = "java(cn.hutool.core.util.StrUtil.split(entity.getTargetUserIds(),\",\"))")})
    NoticeForm toForm(Notice entity);

    @Mappings({@Mapping(target = "targetUserIds",
        expression = "java(cn.hutool.core.collection.CollUtil.join(formData.getTargetUserIds(),\",\"))")})
    Notice toEntity(NoticeForm formData);

    NoticePageVO toPageVo(NoticeBO bo);

    Page<NoticePageVO> toPageVo(Page<NoticeBO> noticePage);

    NoticeDetailVO toDetailVO(NoticeBO noticeBO);
}
