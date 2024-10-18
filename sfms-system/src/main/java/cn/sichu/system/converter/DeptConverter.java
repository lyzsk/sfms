package cn.sichu.system.converter;

import cn.sichu.system.model.entity.Dept;
import cn.sichu.system.model.form.DeptForm;
import cn.sichu.system.model.vo.DeptVO;
import org.mapstruct.Mapper;

/**
 * 部门对象转换器
 *
 * @author sichu huang
 * @since 2024/10/16 23:29:22
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm toForm(Dept entity);

    DeptVO toVo(Dept entity);

    Dept toEntity(DeptForm deptForm);

}