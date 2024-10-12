package cn.sichu.auth.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.auth.model.query.OnlineUserQuery;
import cn.sichu.auth.model.resp.OnlineUserResp;
import cn.sichu.auth.service.IOnlineUserService;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.mp.model.resp.PageResp;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.utils.helper.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 在线用户业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class OnlineUserServiceImpl implements IOnlineUserService {

    @Override
    @AutoOperate(type = OnlineUserResp.class, on = "list")
    public PageResp<OnlineUserResp> page(OnlineUserQuery query, PageQuery pageQuery) {
        List<LoginUser> loginUserList = this.list(query);
        List<OnlineUserResp> list = BeanUtil.copyToList(loginUserList, OnlineUserResp.class);
        return PageResp.build(pageQuery.getPage(), pageQuery.getSize(), list);
    }

    @Override
    public List<LoginUser> list(OnlineUserQuery query) {
        List<LoginUser> loginUserList = new ArrayList<>();
        // 查询所有登录用户
        List<String> tokenKeyList = StpUtil.searchTokenValue(StringConstants.EMPTY, 0, -1, false);
        tokenKeyList.parallelStream().forEach(tokenKey -> {
            String token = StrUtil.subAfter(tokenKey, StringConstants.COLON, true);
            // 忽略已过期或失效 Token
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < SaTokenDao.NEVER_EXPIRE) {
                return;
            }
            // 检查是否符合查询条件
            LoginUser loginUser = LoginHelper.getLoginUser(token);
            if (this.isMatchQuery(query, loginUser)) {
                loginUserList.add(loginUser);
            }
        });
        // 设置排序
        CollUtil.sort(loginUserList, Comparator.comparing(LoginUser::getLoginTime).reversed());
        return loginUserList;
    }

    @Override
    public Date getLastActiveTime(String token) {
        long lastActiveTime = StpUtil.getStpLogic().getTokenLastActiveTime(token);
        return lastActiveTime == SaTokenDao.NOT_VALUE_EXPIRE ? null : new Date(lastActiveTime);
    }

    @Override
    public void kickOut(Long userId) {
        if (!StpUtil.isLogin(userId)) {
            return;
        }
        StpUtil.logout(userId);
    }

    /**
     * 是否符合查询条件
     *
     * @param query     查询条件
     * @param loginUser 登录用户信息
     * @return 是否符合查询条件
     */
    private boolean isMatchQuery(OnlineUserQuery query, LoginUser loginUser) {
        boolean flag1 = true;
        String nickname = query.getNickname();
        if (StrUtil.isNotBlank(nickname)) {
            flag1 = StrUtil.contains(loginUser.getUsername(), nickname) || StrUtil.contains(
                LoginHelper.getNickname(loginUser.getId()), nickname);
        }
        boolean flag2 = true;
        List<Date> loginTime = query.getLoginTime();
        if (CollUtil.isNotEmpty(loginTime)) {
            flag2 =
                DateUtil.isIn(DateUtil.date(loginUser.getLoginTime()).toJdkDate(), loginTime.get(0),
                    loginTime.get(1));
        }
        boolean flag3 = true;
        Long userId = query.getUserId();
        if (null != userId) {
            flag3 = userId.equals(loginUser.getId());
        }
        boolean flag4 = true;
        Long roleId = query.getRoleId();
        if (null != roleId) {
            flag4 = loginUser.getRoles().stream().anyMatch(r -> r.getId().equals(roleId));
        }
        return flag1 && flag2 && flag3 && flag4;
    }
}
