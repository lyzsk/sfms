INSERT INTO sfms.sys_dept
(id, name, code, parent_id, tree_path, sort, status, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(1, 'lyzsk Tech', 'LYZSK', 0, '0', 1, 1, NULL, '2024-10-01 12:34:56.789', NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dept
(id, name, code, parent_id, tree_path, sort, status, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(2, '研发部门', 'DEV001', 1, '0,1', 1, 1, NULL, '2024-10-01 12:34:56.789', NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dept
(id, name, code, parent_id, tree_path, sort, status, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(3, '测试部门', 'TEST0001', 1, '0,1', 2, 1, 2, '2024-10-18 23:08:19.694', NULL, '2024-10-18 23:08:19.695', 0, NULL);

INSERT INTO sfms.sys_dict
(id, dict_code, name, status, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(1, 'gender', '性别', 1, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict
(id, dict_code, name, status, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(2, 'notice_type', '通知类型', 1, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict
(id, dict_code, name, status, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(3, 'notice_level', '通知级别', 1, NULL, NULL, NULL, NULL, 0, NULL);

INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(1, 'gender', '1', '男', 'primary', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(2, 'gender', '2', '女', 'danger', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(3, 'gender', '0', '保密', 'info', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(4, 'notice_type', '1', '系统升级', 'success', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(5, 'notice_type', '2', '系统维护', 'primary', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(6, 'notice_type', '3', '安全警告', 'danger', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(7, 'notice_type', '4', '假期通知', 'success', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(8, 'notice_type', '5', '公司新闻', 'primary', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(9, 'notice_type', '99', '其他', 'info', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(10, 'notice_level', 'L', '低', 'info', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(11, 'notice_level', 'M', '中', 'warning', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_dict_data
(id, dict_code, value, label, tag_type, status, sort, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(12, 'notice_level', 'H', '高', 'danger', 1, 0, NULL, NULL, NULL, NULL, 0, NULL);

INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(1, 0, '0', '系统管理', 2, NULL, '/system', 'Layout', NULL, 0, 0, 1, 1, 'system', '/system/user', NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(2, 1, '0,1', '用户管理', 1, 'User', 'user', 'system/user/index', NULL, 0, 1, 1, 1, 'el-icon-User', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(3, 1, '0,1', '角色管理', 1, 'Role', 'role', 'system/role/index', NULL, 0, 1, 1, 2, 'role', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(4, 1, '0,1', '菜单管理', 1, 'Menu', 'menu', 'system/menu/index', NULL, 0, 1, 1, 3, 'menu', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(5, 1, '0,1', '部门管理', 1, 'Dept', 'dept', 'system/dept/index', NULL, 0, 1, 1, 4, 'tree', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(6, 1, '0,1', '字典管理', 1, 'Dict', 'dict', 'system/dict/index', NULL, 0, 1, 1, 5, 'dict', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(7, 2, '0,1,2', '用户新增', 4, NULL, '', NULL, 'sys:user:add', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(8, 2, '0,1,2', '用户编辑', 4, NULL, '', NULL, 'sys:user:edit', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(9, 2, '0,1,2', '用户删除', 4, NULL, '', NULL, 'sys:user:delete', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(10, 0, '0', '接口文档', 2, NULL, '/api', 'Layout', NULL, 1, 0, 1, 3, 'api', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(11, 10, '0,10', 'Apifox', 1, NULL, 'apifox', 'demo/api/apifox', NULL, 0, 1, 1, 1, 'api', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(12, 3, '0,1,3', '角色新增', 4, NULL, '', NULL, 'sys:role:add', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(13, 3, '0,1,3', '角色编辑', 4, NULL, '', NULL, 'sys:role:edit', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(14, 3, '0,1,3', '角色删除', 4, NULL, '', NULL, 'sys:role:delete', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(15, 4, '0,1,4', '菜单新增', 4, NULL, '', NULL, 'sys:menu:add', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(16, 4, '0,1,4', '菜单编辑', 4, NULL, '', NULL, 'sys:menu:edit', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(17, 4, '0,1,4', '菜单删除', 4, NULL, '', NULL, 'sys:menu:delete', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(18, 5, '0,1,5', '部门新增', 4, NULL, '', NULL, 'sys:dept:add', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(19, 5, '0,1,5', '部门编辑', 4, NULL, '', NULL, 'sys:dept:edit', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(20, 5, '0,1,5', '部门删除', 4, NULL, '', NULL, 'sys:dept:delete', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(21, 6, '0,1,6', '字典新增', 4, NULL, '', NULL, 'sys:dict:add', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(22, 6, '0,1,6', '字典编辑', 4, NULL, '', NULL, 'sys:dict:edit', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(23, 6, '0,1,6', '字典删除', 4, NULL, '', NULL, 'sys:dict:delete', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(24, 2, '0,1,2', '重置密码', 4, NULL, '', NULL, 'sys:user:password:reset', 0, 0, 1, 4, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(25, 0, '0', '功能演示', 2, NULL, '/function', 'Layout', NULL, 0, 0, 1, 4, 'menu', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(26, 25, '0,25', 'Websocket', 1, NULL, '/function/websocket', 'demo/websocket', NULL, 0, 1, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(27, 2, '0,1,2', '用户查询', 4, NULL, '', NULL, 'sys:user:query', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(28, 2, '0,1,2', '用户导入', 4, NULL, '', NULL, 'sys:user:import', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(29, 2, '0,1,2', '用户导出', 4, NULL, '', NULL, 'sys:user:export', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(30, 1, '0,1', '系统日志', 1, 'Log', 'log', 'system/log/index', NULL, 0, 0, 1, 9, 'document', NULL, NULL, NULL, NULL, NULL, '2024-10-18 22:14:47.658', 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(31, 1, '0,1', '系统配置', 1, 'Config', 'config', 'system/config/index', NULL, 0, 0, 1, 7, 'setting', NULL, NULL, NULL, NULL, NULL, '2024-10-18 22:14:38.696', 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(32, 31, '0,1,31', '查询系统配置', 4, NULL, '', NULL, 'sys:config:query', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(33, 31, '0,1,31', '新增系统配置', 4, NULL, '', NULL, 'sys:config:add', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(34, 31, '0,1,31', '修改系统配置', 4, NULL, '', NULL, 'sys:config:update', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(35, 31, '0,1,31', '删除系统配置', 4, NULL, '', NULL, 'sys:config:delete', 0, 0, 1, 4, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(36, 31, '0,1,31', '刷新系统配置', 4, NULL, '', NULL, 'sys:config:refresh', 0, 1, 1, 5, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(37, 1, '0,1', '通知公告', 1, 'Notice', 'notice', 'system/notice/index', NULL, 0, 0, 1, 8, '', NULL, NULL, NULL, NULL, NULL, '2024-10-18 22:14:42.837', 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(38, 37, '0,1,37', '公告查询', 4, NULL, '', NULL, 'sys:notice:query', 0, 0, 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(39, 37, '0,1,37', '公告新增', 4, NULL, '', NULL, 'sys:notice:add', 0, 0, 1, 2, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(40, 37, '0,1,37', '公告编辑', 4, NULL, '', NULL, 'sys:notice:edit', 0, 0, 1, 3, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(41, 37, '0,1,37', '公告删除', 4, NULL, '', NULL, 'sys:notice:delete', 0, 0, 1, 4, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(42, 37, '0,1,37', '公告发布', 4, NULL, '', NULL, 'sys:notice:publish', 0, 1, 1, 5, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(43, 37, '0,1,37', '公告撤回', 4, NULL, '', NULL, 'sys:notice:revoke', 0, 1, 1, 6, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(44, 1, '0,1', '字典数据', 1, 'DictData', 'dict-data', 'system/dict/data', NULL, 0, 1, 0, 6, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(45, 44, '0,1,44', '字典数据新增', 4, NULL, '', NULL, 'sys:dict-data:add', 0, NULL, 1, 0, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(46, 44, '0,1,44', '字典数据编辑', 4, NULL, '', NULL, 'sys:dict-data:edit', 0, NULL, 1, 0, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_menu
(id, parent_id, tree_path, name, `type`, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, params, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(47, 44, '0,1,44', '字典数据删除', 4, NULL, '', NULL, 'sys:dict-data:delete', 0, NULL, 1, 0, '', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);

INSERT INTO sfms.sys_role
(id, name, code, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(1, '超级管理员', 'ADMIN', 1, 1, 0, NULL, '2024-10-01 12:34:56.789', NULL, NULL, 0, NULL);
INSERT INTO sfms.sys_role
(id, name, code, sort, status, data_scope, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(2, '系统测试员', 'TEST', 2, 1, 1, NULL, '2024-10-01 12:34:56.789', NULL, NULL, 0, NULL);

INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 1);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 2);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 3);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 4);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 5);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 6);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 7);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 8);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 9);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 10);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 11);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 12);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 13);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 14);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 15);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 16);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 17);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 18);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 19);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 20);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 21);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 22);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 23);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 24);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 25);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 26);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 27);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 28);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 29);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 30);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 31);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 32);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 33);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 34);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 35);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 36);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 37);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 38);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 39);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 40);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 41);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 42);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 43);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 44);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 45);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 46);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(1, 47);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(2, 1);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(2, 25);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(2, 26);
INSERT INTO sfms.sys_role_menu
(role_id, menu_id)
VALUES(2, 30);

INSERT INTO sfms.sys_user
(id, username, nickname, gender, password, dept_id, avatar, mobile, status, email, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(1, 'admin', '超级管理员', 1, '$2a$10$fv8g9pSqMY3UgbVYDK1mQuIKMm6gmvo9gijLKYOd5RodXUm6U08YW', 2, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', NULL, 1, NULL, NULL, '2024-10-01 12:34:56.789', NULL, '2024-10-18 23:25:41.228', 0, NULL);
INSERT INTO sfms.sys_user
(id, username, nickname, gender, password, dept_id, avatar, mobile, status, email, create_by, create_time, update_by, update_time, is_deleted, remark)
VALUES(2, 'test', '系统测试员', 2, '$2a$10$NR7oOHRH5KnfouO0vNmwnewnB1U4mIaOusT.rgbILCNtF.efLE.Zy', 3, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', NULL, 1, NULL, NULL, '2024-10-01 12:34:56.789', NULL, '2024-10-18 23:08:40.358', 0, NULL);

INSERT INTO sfms.sys_user_role
(user_id, role_id)
VALUES(1, 1);
INSERT INTO sfms.sys_user_role
(user_id, role_id)
VALUES(2, 2);