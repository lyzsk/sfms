package cn.sichu.data.mp.service.impl;

import cn.hutool.core.util.ClassUtil;
import cn.sichu.core.utils.ReflectUtils;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.data.mp.service.IService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 通用业务实现类
 *
 * @param <M> Mapper 接口
 * @param <T> 实体类型
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ServiceImpl<M extends BaseMapper<T>, T>
    extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M, T>
    implements IService<T> {

    private List<Field> entityFields;

    @Override
    public T getById(Serializable id) {
        return this.getById(id, true);
    }

    /**
     * 获取当前实体类型字段
     *
     * @return 当前实体类型字段列表
     */
    public List<Field> getEntityFields() {
        if (this.entityFields == null) {
            this.entityFields = ReflectUtils.getNonStaticFields(this.getEntityClass());
        }
        return this.entityFields;
    }

    /**
     * 根据 ID 查询
     *
     * @param id            ID
     * @param isCheckExists 是否检查存在
     * @return 实体信息
     */
    protected T getById(Serializable id, boolean isCheckExists) {
        T entity = baseMapper.selectById(id);
        if (isCheckExists) {
            CheckUtils.throwIfNotExists(entity, ClassUtil.getClassName(this.getEntityClass(), true),
                "ID", id);
        }
        return entity;
    }
}