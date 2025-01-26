# sfms

semiconductor fabrication management system, 半导体 FAB 管理系统

# Environment

1. java -version: 17.0.11
2. mvn -v: 3.9.9
3. node -v: 20.17.0

# 功能

1. SpringSecurity+JWT 登录认证
2. 角色权限管理
3. 菜单管理 && 按钮管理
4. 字典管理

# 2024-10-18

修改所有数据库 datetime -> datetime(3)

TODO: mp 默认映射 datetime -> java.util.Date 所以 BO 是 Date 类型, 然后 toPageVO 也是 Date 类型
有空尝试用 handler 的方式处理强制映射成 LocalDateTime
