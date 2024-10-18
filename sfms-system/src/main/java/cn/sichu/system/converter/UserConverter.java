package cn.sichu.system.converter;

import cn.sichu.system.model.bo.UserBO;
import cn.sichu.system.model.dto.UserImportDTO;
import cn.sichu.system.model.entity.User;
import cn.sichu.system.model.form.UserForm;
import cn.sichu.system.model.form.UserProfileForm;
import cn.sichu.system.model.vo.UserInfoVO;
import cn.sichu.system.model.vo.UserPageVO;
import cn.sichu.system.model.vo.UserProfileVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 用户对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:33:25
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({@Mapping(target = "genderLabel",
        expression = "java(cn.sichu.base.IBaseEnum.getLabelByValue(bo.getGender(), cn.sichu.system.enums.GenderEnum.class))")})
    UserPageVO toPageVo(UserBO bo);

    Page<UserPageVO> toPageVo(Page<UserBO> bo);

    UserForm toForm(User entity);

    @InheritInverseConfiguration(name = "toForm")
    User toEntity(UserForm entity);

    @Mappings({@Mapping(target = "userId", source = "id")})
    UserInfoVO toUserInfoVo(User entity);

    User toEntity(UserImportDTO vo);

    UserProfileVO toProfileVO(UserBO bo);

    User toEntity(UserProfileForm formData);
}
