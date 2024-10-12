package cn.sichu.config.mybatis;

import cn.hutool.core.convert.Convert;
import cn.sichu.data.mp.datapermission.DataPermissionCurrentUser;
import cn.sichu.data.mp.datapermission.DataPermissionFilter;
import cn.sichu.data.mp.datapermission.DataScope;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.utils.helper.LoginHelper;

import java.util.stream.Collectors;

/**
 * 数据权限过滤器实现类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class DataPermissionFilterImpl implements DataPermissionFilter {

    @Override
    public boolean isFilter() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        return !loginUser.isAdmin();
    }

    @Override
    public DataPermissionCurrentUser getCurrentUser() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        DataPermissionCurrentUser currentUser = new DataPermissionCurrentUser();
        currentUser.setUserId(Convert.toStr(loginUser.getId()));
        currentUser.setDeptId(Convert.toStr(loginUser.getDeptId()));
        currentUser.setRoles(loginUser.getRoles().stream().map(
            r -> new DataPermissionCurrentUser.CurrentUserRole(Convert.toStr(r.getId()),
                DataScope.valueOf(r.getDataScope().name()))).collect(Collectors.toSet()));
        return currentUser;
    }
}
