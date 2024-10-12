package cn.sichu.config.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.sichu.core.exception.BusinessException;
import cn.sichu.core.utils.DateUtils;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.utils.helper.LoginHelper;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MyBatis Plus 元对象处理器配置（插入或修改时自动填充）
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建人
     **/
    private static final String CREATE_USER = "createUser";
    /**
     * 创建时间
     **/
    private static final String CREATE_TIME = "createTime";
    /**
     * 修改人
     **/
    private static final String UPDATE_USER = "updateUser";
    /**
     * 修改时间
     **/
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 插入数据时填充
     *
     * @param metaObject 元对象
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (null == metaObject) {
                return;
            }
            Long createUser = ExceptionUtils.exToNull(LoginHelper::getUserId);
            Date createTime = DateUtils.parseMillisecond(new Date());
            if (metaObject.getOriginalObject() instanceof BaseDO baseDO) {
                // 继承了 BaseDO 的类，填充创建信息字段
                baseDO.setCreateUser(ObjectUtil.defaultIfNull(baseDO.getCreateUser(), createUser));
                baseDO.setCreateTime(ObjectUtil.defaultIfNull(baseDO.getCreateTime(), createTime));
            } else {
                // 未继承 BaseDO 的类，如存在创建信息字段则进行填充
                this.fillFieldValue(metaObject, CREATE_USER, createUser, false);
                this.fillFieldValue(metaObject, CREATE_TIME, createTime, false);
            }
        } catch (Exception e) {
            throw new BusinessException("插入数据时自动填充异常：" + e.getMessage());
        }
    }

    /**
     * 修改数据时填充
     *
     * @param metaObject 元对象
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (null == metaObject) {
                return;
            }

            Long updateUser = LoginHelper.getUserId();
            Date updateTime = DateUtils.parseMillisecond(new Date());
            if (metaObject.getOriginalObject() instanceof BaseDO baseDO) {
                // 继承了 BaseDO 的类，填充修改信息
                baseDO.setUpdateUser(updateUser);
                baseDO.setUpdateTime(updateTime);
            } else {
                // 未继承 BaseDO 的类，根据类中拥有的修改信息字段进行填充，不存在修改信息字段不进行填充
                this.fillFieldValue(metaObject, UPDATE_USER, updateUser, true);
                this.fillFieldValue(metaObject, UPDATE_TIME, updateTime, true);
            }
        } catch (Exception e) {
            throw new BusinessException("修改数据时自动填充异常：" + e.getMessage());
        }
    }

    /**
     * 填充字段值
     *
     * @param metaObject     元数据对象
     * @param fieldName      要填充的字段名
     * @param fillFieldValue 要填充的字段值
     * @param isOverride     如果字段值不为空，是否覆盖（true：覆盖；false：不覆盖）
     * @author sichu huang
     * @date 2024/10/12
     **/
    private void fillFieldValue(MetaObject metaObject, String fieldName, Object fillFieldValue,
        boolean isOverride) {
        if (metaObject.hasSetter(fieldName)) {
            Object fieldValue = metaObject.getValue(fieldName);
            setFieldValByName(fieldName,
                null != fieldValue && !isOverride ? fieldValue : fillFieldValue, metaObject);
        }
    }
}
