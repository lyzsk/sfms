package cn.sichu.config.mybatis;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.data.mp.datapermission.DataPermission;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限 Mapper 基类
 *
 * @param <T> 实体类
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface DataPermissionMapper<T> extends BaseMapper<T> {

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return java.util.List<T> 全部记录
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Override
    @DataPermission
    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return java.util.List<T> 全部记录（并翻页）
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Override
    @DataPermission
    List<T> selectList(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
