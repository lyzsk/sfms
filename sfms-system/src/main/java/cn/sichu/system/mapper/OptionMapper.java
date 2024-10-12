package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.OptionDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface OptionMapper extends BaseMapper<OptionDO> {
    /**
     * 根据类别查询
     *
     * @param category 类别
     * @return java.util.List<OptionDO> 列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    @Select("SELECT code, value, default_value FROM t_sys_option WHERE category = #{category}")
    List<OptionDO> selectByCategory(@Param("category") String category);
}
