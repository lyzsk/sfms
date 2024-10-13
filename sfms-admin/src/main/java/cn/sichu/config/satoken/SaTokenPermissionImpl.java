package cn.sichu.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.utils.helper.LoginHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 权限认证实现
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
public class SaTokenPermissionImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        return new ArrayList<>(loginUser.getPermissions());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        return new ArrayList<>(loginUser.getRoleCodes());
    }
}
