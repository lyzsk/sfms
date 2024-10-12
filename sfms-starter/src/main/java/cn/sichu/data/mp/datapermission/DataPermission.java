package cn.sichu.data.mp.datapermission;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 表别名
     */
    String tableAlias() default "";

    /**
     * ID
     */
    String id() default "id";

    /**
     * 部门 ID
     */
    String deptId() default "dept_id";

    /**
     * 用户 ID
     */
    String userId() default "create_user";

    /**
     * 角色 ID（角色和部门关联表）
     */
    String roleId() default "role_id";

    /**
     * 部门表别名
     */
    String deptTableAlias() default "t_sys_dept";

    /**
     * 角色和部门关联表别名
     */
    String roleDeptTableAlias() default "t_sys_role_dept";
}
