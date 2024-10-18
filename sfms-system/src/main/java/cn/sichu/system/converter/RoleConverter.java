package cn.sichu.system.converter;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.Role;
import cn.sichu.system.model.form.RoleForm;
import cn.sichu.system.model.vo.RolePageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 角色对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:32:57
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    Page<RolePageVO> toPageVo(Page<Role> page);

    @Mappings(
        {@Mapping(target = "value", source = "id"), @Mapping(target = "label", source = "name")})
    Option<Long> entity2Option(Role role);

    List<Option<Long>> entities2Options(List<Role> roles);

    Role toEntity(RoleForm roleForm);

    RoleForm toForm(Role entity);
}