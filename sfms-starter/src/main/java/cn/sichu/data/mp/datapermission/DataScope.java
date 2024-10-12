package cn.sichu.data.mp.datapermission;

/**
 * 数据权限枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public enum DataScope {

    /**
     * 全部数据权限
     */
    ALL,

    /**
     * 本部门及以下数据权限
     */
    DEPT_AND_CHILD,

    /**
     * 本部门数据权限
     */
    DEPT,

    /**
     * 仅本人数据权限
     */
    SELF,

    /**
     * 自定义数据权限
     */
    CUSTOM,
}
