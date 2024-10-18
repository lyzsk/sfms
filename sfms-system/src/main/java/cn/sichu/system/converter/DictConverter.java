package cn.sichu.system.converter;

import cn.sichu.system.model.entity.Dict;
import cn.sichu.system.model.form.DictForm;
import cn.sichu.system.model.vo.DictPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

/**
 * 字典 对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:30:06
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> toPageVo(Page<Dict> page);

    DictForm toForm(Dict entity);

    Dict toEntity(DictForm entity);
}
