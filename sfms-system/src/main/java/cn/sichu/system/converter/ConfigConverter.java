package cn.sichu.system.converter;

import cn.sichu.system.model.entity.Config;
import cn.sichu.system.model.form.ConfigForm;
import cn.sichu.system.model.vo.ConfigVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

/**
 * 系统配置对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:28:47
 */
@Mapper(componentModel = "spring")
public interface ConfigConverter {

    Page<ConfigVO> toPageVo(Page<Config> page);

    Config toEntity(ConfigForm configForm);

    ConfigForm toForm(Config entity);
}
