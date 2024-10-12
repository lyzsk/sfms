package cn.sichu.data.mp.base;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;

import java.util.Collection;

/**
 * Mapper 基类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量插入记录
     *
     * @param entityList 实体列表
     * @return 是否成功
     */
    default boolean insertBatch(Collection<T> entityList) {
        return Db.saveBatch(entityList);
    }

    /**
     * 批量更新记录
     *
     * @param entityList 实体列表
     * @return 是否成功
     */
    default boolean updateBatchById(Collection<T> entityList) {
        return Db.updateBatchById(entityList);
    }

    /**
     * 链式查询
     *
     * @return QueryWrapper 的包装类
     */
    default QueryChainWrapper<T> query() {
        return ChainWrappers.queryChain(this);
    }

    /**
     * 链式查询（lambda 式）
     *
     * @return LambdaQueryWrapper 的包装类
     */
    default LambdaQueryChainWrapper<T> lambdaQuery() {
        return ChainWrappers.lambdaQueryChain(this, this.currentEntityClass());
    }

    /**
     * 链式查询（lambda 式）
     *
     * @param entity 实体对象
     * @return LambdaQueryWrapper 的包装类
     */
    default LambdaQueryChainWrapper<T> lambdaQuery(T entity) {
        return ChainWrappers.lambdaQueryChain(this, entity);
    }

    /**
     * 链式更改
     *
     * @return UpdateWrapper 的包装类
     */
    default UpdateChainWrapper<T> update() {
        return ChainWrappers.updateChain(this);
    }

    /**
     * 链式更改（lambda 式）
     *
     * @return LambdaUpdateWrapper 的包装类
     */
    default LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return ChainWrappers.lambdaUpdateChain(this);
    }

    /**
     * 获取实体类 Class 对象
     *
     * @return 实体类 Class 对象
     */
    default Class<T> currentEntityClass() {
        return (Class<T>)ClassUtil.getTypeArgument(this.getClass(), 0);
    }
}
