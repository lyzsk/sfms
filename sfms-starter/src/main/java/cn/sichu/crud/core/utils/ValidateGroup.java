package cn.sichu.crud.core.utils;

import jakarta.validation.groups.Default;

/**
 * 分组校验
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface ValidateGroup extends Default {

    /**
     * 分组校验-增删改查
     */
    interface Crud extends ValidateGroup {
        /**
         * 分组校验-创建
         */
        interface Add extends Crud {
        }

        /**
         * 分组校验-修改
         */
        interface Update extends Crud {
        }
    }
}
