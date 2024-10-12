# sfms

semiconductor fabrication management system, 半导体 FAB 管理系统

# Environment

1. java -version: 17.0.11
2. mvn -v: 3.9.9
3. node -v: 20.17.0

# TODO LIST

- [] 用户管理
- [] 角色管理
- [] 菜单管理
- [] 部门管理
- [] 用户登录
- [] 用户注册
- [] 数据大屏

#

## login

-   [x] /login view
- [] /login api
- [] /login backend
- [] /login router
- [] /home view
- [] /login backend
- [] /login router

SITE_TITLE, SITE_DESCRIPTION, SITE_COPYRIGHT, SITE_BEIAN, etc. 存在 sys_option
中, 并存入 redis, 前端直接通过 api 初始化系统配置

TODO: login index.vue 中的 .login-right 明显在 login-box 中 只占了 12 栅格,
怎么修改外层宽度和样式?

修改了 DisEnableStatusEnum, 从 1-启用, 2-禁用, 修改为: 0-禁用, 1-启用

修改了所有 LocalDateTime 类型为 Date 类型, 同理计算方式:

```java
    public boolean isPasswordExpired() {
        // 永久有效
        if (this.passwordExpirationDays == null
            || this.passwordExpirationDays <= SysConstants.NO) {
            return false;
        }
        // 初始密码（第三方登录用户）暂不提示修改
        if (this.pwdResetTime == null) {
            return false;
        }
        long expirationTimeInMillis = this.pwdResetTime.getTime()
            + this.passwordExpirationDays * 24 * 60 * 60 * 1000L;
        Date expirationDate = new Date(expirationTimeInMillis);
        return expirationDate.before(new Date());
    }
```

TODO: `RateIntervalUnit.SECONDS`
'org.redisson.api.RateIntervalUnit' is deprecated 3.36.0 -> redisson-3.37.0

TODO: `[SFMS]` 全局修改成 `[SFMS]`
