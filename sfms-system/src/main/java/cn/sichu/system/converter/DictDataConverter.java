package cn.sichu.system.converter;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.DictData;
import cn.sichu.system.model.form.DictDataForm;
import cn.sichu.system.model.vo.DictPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典项 对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:30:40
 */
@Mapper(componentModel = "spring")
public interface DictDataConverter {

    Page<DictPageVO> toPageVo(Page<DictData> page);

    DictDataForm toForm(DictData entity);

    DictData toEntity(DictDataForm formFata);

    Option<Long> toOption(DictData dictData);

    List<Option<Long>> toOption(List<DictData> dictData);
}
