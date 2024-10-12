package cn.sichu.system.utils;

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
     **/
    interface Storage extends ValidateGroup {
        /**
         * 本地存储
         **/
        interface Local extends Storage {
        }

        /**
         * 兼容S3协议存储
         **/
        interface S3 extends Storage {
        }
    }
}
