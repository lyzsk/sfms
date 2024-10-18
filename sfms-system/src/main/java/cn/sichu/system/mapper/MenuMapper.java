package cn.sichu.system.mapper;

import cn.sichu.system.model.bo.RouteBO;
import cn.sichu.system.model.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author sichu huang
 * @since 2024/10/16 23:22
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取菜单路由列表
     *
     * @param roles roles
     * @return java.util.List<cn.sichu.model.bo.RouteBO>
     * @author sichu huang
     * @since 2024/10/16 23:22:16
     */
    List<RouteBO> listRoutes(Set<String> roles);
}
