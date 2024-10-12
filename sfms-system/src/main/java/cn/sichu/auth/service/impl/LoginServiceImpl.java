package cn.sichu.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.sichu.auth.model.resp.RouteResp;
import cn.sichu.auth.service.ILoginService;
import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.constant.CacheConstants;
import cn.sichu.constant.SysConstants;
import cn.sichu.core.autoconfigure.project.ProjectProperties;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.annotation.TreeField;
import cn.sichu.crud.core.utils.TreeUtils;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.model.dto.RoleDTO;
import cn.sichu.system.enums.MenuTypeEnum;
import cn.sichu.system.enums.PasswordPolicyEnum;
import cn.sichu.system.model.entity.DeptDO;
import cn.sichu.system.model.entity.UserDO;
import cn.sichu.system.model.resp.MenuResp;
import cn.sichu.system.service.*;
import cn.sichu.utils.helper.LoginHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static cn.sichu.system.enums.PasswordPolicyEnum.PASSWORD_EXPIRATION_DAYS;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {
    private final ProjectProperties projectProperties;
    private final PasswordEncoder passwordEncoder;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final IUserService userService;
    private final IDeptService deptService;
    private final IRoleService roleService;
    private final IMenuService menuService;
    private final IUserRoleService userRoleService;
    private final IOptionService optionService;
    private final IMessageService messageService;

    @Override
    public String accountLogin(String username, String password, HttpServletRequest request)
        throws ParseException {
        UserDO user = userService.getByUsername(username);
        boolean isError =
            ObjectUtil.isNull(user) || !passwordEncoder.matches(password, user.getPassword());
        this.checkUserLocked(username, request, isError);
        CheckUtils.throwIf(isError, "用户名或密码错误");
        this.checkUserStatus(user);
        return this.login(user);
    }

    @Override
    public String phoneLogin(String phone) throws ParseException {
        UserDO user = userService.getByPhone(phone);
        CheckUtils.throwIfNull(user, "此手机号未绑定本系统账号");
        this.checkUserStatus(user);
        return this.login(user);
    }

    @Override
    public String emailLogin(String email) throws ParseException {
        UserDO user = userService.getByEmail(email);
        CheckUtils.throwIfNull(user, "此邮箱未绑定本系统账号");
        this.checkUserStatus(user);
        return this.login(user);
    }

    @Override
    public List<RouteResp> buildRouteTree(Long userId) {
        Set<String> roleCodeSet = roleService.listCodeByUserId(userId);
        if (CollUtil.isEmpty(roleCodeSet)) {
            return new ArrayList<>(0);
        }
        // 查询菜单列表
        Set<MenuResp> menuSet = new LinkedHashSet<>();
        if (roleCodeSet.contains(SysConstants.ADMIN_ROLE_CODE)) {
            menuSet.addAll(menuService.listAll());
        } else {
            roleCodeSet.forEach(roleCode -> menuSet.addAll(menuService.listByRoleCode(roleCode)));
        }
        List<MenuResp> menuList =
            menuSet.stream().filter(m -> !MenuTypeEnum.BUTTON.equals(m.getType())).toList();
        // 构建路由树
        TreeField treeField = MenuResp.class.getDeclaredAnnotation(TreeField.class);
        TreeNodeConfig treeNodeConfig = TreeUtils.genTreeNodeConfig(treeField);
        List<Tree<Long>> treeList = TreeUtils.build(menuList, treeNodeConfig, (m, tree) -> {
            tree.setId(m.getId());
            tree.setParentId(m.getParentId());
            tree.setName(m.getTitle());
            tree.setWeight(m.getSort());
            tree.putExtra("type", m.getType().getValue());
            tree.putExtra("path", m.getPath());
            tree.putExtra("name", m.getName());
            tree.putExtra("component", m.getComponent());
            tree.putExtra("redirect", m.getRedirect());
            tree.putExtra("icon", m.getIcon());
            tree.putExtra("isExternal", m.getIsExternal());
            tree.putExtra("isCache", m.getIsCache());
            tree.putExtra("isHidden", m.getIsHidden());
            tree.putExtra("permission", m.getPermission());
        });
        return BeanUtil.copyToList(treeList, RouteResp.class);
    }

    /**
     * 登录并缓存用户信息
     *
     * @param user 用户信息
     * @return java.lang.String 令牌
     * @author sichu huang
     * @date 2024/10/12
     **/
    private String login(UserDO user) throws ParseException {
        Long userId = user.getId();
        CompletableFuture<Set<String>> permissionFuture =
            CompletableFuture.supplyAsync(() -> roleService.listPermissionByUserId(userId),
                threadPoolTaskExecutor);
        CompletableFuture<Set<RoleDTO>> roleFuture =
            CompletableFuture.supplyAsync(() -> roleService.listByUserId(userId),
                threadPoolTaskExecutor);
        CompletableFuture<Integer> passwordExpirationDaysFuture = CompletableFuture.supplyAsync(
            () -> optionService.getValueByCode2Int(PASSWORD_EXPIRATION_DAYS.name()));
        CompletableFuture.allOf(permissionFuture, roleFuture, passwordExpirationDaysFuture);
        LoginUser loginUser = new LoginUser(permissionFuture.join(), roleFuture.join(),
            passwordExpirationDaysFuture.join());
        BeanUtil.copyProperties(user, loginUser);
        return LoginHelper.login(loginUser);
    }

    /**
     * 检查用户状态
     *
     * @param user 用户信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    private void checkUserStatus(UserDO user) {
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, user.getStatus(),
            "此账号已被禁用，如有疑问，请联系管理员");
        DeptDO dept = deptService.getById(user.getDeptId());
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, dept.getStatus(),
            "此账号所属部门已被禁用，如有疑问，请联系管理员");
    }

    /**
     * 检测用户是否已被锁定
     *
     * @param username 用户名
     * @param request  请求对象
     * @param isError  是否登录错误
     * @author sichu huang
     * @date 2024/10/12
     **/
    private void checkUserLocked(String username, HttpServletRequest request, boolean isError) {
        // 不锁定
        int maxErrorCount =
            optionService.getValueByCode2Int(PasswordPolicyEnum.PASSWORD_ERROR_LOCK_COUNT.name());
        if (maxErrorCount <= SysConstants.NO) {
            return;
        }
        // 检测是否已被锁定
        String key = CacheConstants.USER_PASSWORD_ERROR_KEY_PREFIX + RedisUtils.formatKey(username,
            JakartaServletUtil.getClientIP(request));
        int lockMinutes =
            optionService.getValueByCode2Int(PasswordPolicyEnum.PASSWORD_ERROR_LOCK_MINUTES.name());
        Integer currentErrorCount = ObjectUtil.defaultIfNull(RedisUtils.get(key), 0);
        CheckUtils.throwIf(currentErrorCount >= maxErrorCount, "账号锁定 {} 分钟，请稍后再试",
            lockMinutes);
        // 登录成功清除计数
        if (!isError) {
            RedisUtils.delete(key);
            return;
        }
        // 登录失败递增计数
        currentErrorCount++;
        RedisUtils.set(key, currentErrorCount, Duration.ofMinutes(lockMinutes));
        CheckUtils.throwIf(currentErrorCount >= maxErrorCount,
            "密码错误已达 {} 次，账号锁定 {} 分钟", maxErrorCount, lockMinutes);
    }
}
