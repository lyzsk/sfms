package cn.sichu.system.converter;

import cn.sichu.system.model.entity.Menu;
import cn.sichu.system.model.form.MenuForm;
import cn.sichu.system.model.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 菜单对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:31:43
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO toVo(Menu entity);

    @Mapping(target = "params", ignore = true)
    MenuForm toForm(Menu entity);

    @Mapping(target = "params", ignore = true)
    Menu toEntity(MenuForm menuForm);

}