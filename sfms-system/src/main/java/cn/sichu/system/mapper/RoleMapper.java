package cn.sichu.system.mapper;

import cn.sichu.system.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author sichu huang
 * @since 2024/10/16 23:23
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取最大范围的数据权限
     *
     * @param roles roles
     * @return java.lang.Integer
     * @author sichu huang
     * @since 2024/10/16 23:23:44
     */
    Integer getMaximumDataScope(Set<String> roles);
}
