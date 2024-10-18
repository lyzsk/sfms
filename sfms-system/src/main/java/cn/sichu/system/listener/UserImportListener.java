package cn.sichu.system.listener;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.sichu.base.BaseAnalysisEventListener;
import cn.sichu.base.IBaseEnum;
import cn.sichu.constant.SystemConstants;
import cn.sichu.enums.StatusEnum;
import cn.sichu.system.converter.UserConverter;
import cn.sichu.system.enums.GenderEnum;
import cn.sichu.system.model.dto.UserImportDTO;
import cn.sichu.system.model.entity.Dept;
import cn.sichu.system.model.entity.Role;
import cn.sichu.system.model.entity.User;
import cn.sichu.system.model.entity.UserRole;
import cn.sichu.system.service.DeptService;
import cn.sichu.system.service.RoleService;
import cn.sichu.system.service.UserRoleService;
import cn.sichu.system.service.UserService;
import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户导入监听器
 *
 * @author sichu huang
 * @since 2024/10/17 15:40
 */
@Slf4j
public class UserImportListener extends BaseAnalysisEventListener<UserImportDTO> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final DeptService deptService;

    /**
     * 导入返回信息
     */
    StringBuilder msg = new StringBuilder();

    /**
     * 有效条数
     */
    private int validCount;

    /**
     * 无效条数
     */
    private int invalidCount;

    public UserImportListener() {
        this.userService = SpringUtil.getBean(UserService.class);
        this.passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);
        this.roleService = SpringUtil.getBean(RoleService.class);
        this.userRoleService = SpringUtil.getBean(UserRoleService.class);
        this.deptService = SpringUtil.getBean(DeptService.class);
        this.userConverter = SpringUtil.getBean(UserConverter.class);
    }

    /**
     * 每一条数据解析都会来调用
     * <p>
     * 1. 数据校验；全字段校验
     * 2. 数据持久化；
     *
     * @param userImportDTO 一行数据，类似于 {@link AnalysisContext#readRowHolder()}
     */
    @Override
    public void invoke(UserImportDTO userImportDTO, AnalysisContext analysisContext) {
        log.info("解析到一条用户数据:{}", JSONUtil.toJsonStr(userImportDTO));
        // 校验数据
        StringBuilder validationMsg = new StringBuilder();

        String username = userImportDTO.getUsername();
        if (StrUtil.isBlank(username)) {
            validationMsg.append("用户名为空；");
        } else {
            long count =
                userService.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            if (count > 0) {
                validationMsg.append("用户名已存在；");
            }
        }

        String nickname = userImportDTO.getNickname();
        if (StrUtil.isBlank(nickname)) {
            validationMsg.append("用户昵称为空；");
        }

        String mobile = userImportDTO.getMobile();
        if (StrUtil.isBlank(mobile)) {
            validationMsg.append("手机号码为空；");
        } else {
            if (!Validator.isMobile(mobile)) {
                validationMsg.append("手机号码不正确；");
            }
        }
        if (validationMsg.isEmpty()) {
            // 校验通过，持久化至数据库
            User entity = userConverter.toEntity(userImportDTO);
            entity.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));   // 默认密码
            // 性别逆向解析
            String genderLabel = userImportDTO.getGenderLabel();
            if (StrUtil.isNotBlank(genderLabel)) {
                Integer genderValue =
                    (Integer)IBaseEnum.getValueByLabel(genderLabel, GenderEnum.class);
                entity.setGender(genderValue);
            }
            // 角色解析
            String roleCodes = userImportDTO.getRoleCodes();
            List<Long> roleIds = null;
            if (StrUtil.isNotBlank(roleCodes)) {
                roleIds = roleService.list(
                        new LambdaQueryWrapper<Role>().in(Role::getCode, (Object)roleCodes.split(","))
                            .eq(Role::getStatus, StatusEnum.ENABLE.getValue()).select(Role::getId))
                    .stream().map(Role::getId).collect(Collectors.toList());
            }
            // 部门解析
            String deptCode = userImportDTO.getDeptCode();
            if (StrUtil.isNotBlank(deptCode)) {
                Dept dept = deptService.getOne(
                    new LambdaQueryWrapper<Dept>().eq(Dept::getCode, deptCode).select(Dept::getId));
                if (dept != null) {
                    entity.setDeptId(dept.getId());
                }
            }

            boolean saveResult = userService.save(entity);
            if (saveResult) {
                validCount++;
                // 保存用户角色关联
                if (CollectionUtil.isNotEmpty(roleIds)) {
                    List<UserRole> userRoles =
                        roleIds.stream().map(roleId -> new UserRole(entity.getId(), roleId))
                            .collect(Collectors.toList());
                    userRoleService.saveBatch(userRoles);
                }
            } else {
                invalidCount++;
                msg.append("第").append(validCount + invalidCount).append("行数据保存失败；<br/>");
            }
        } else {
            invalidCount++;
            msg.append("第").append(validCount + invalidCount).append("行数据校验失败：")
                .append(validationMsg).append("<br/>");
        }
    }

    /**
     * 所有数据解析完成会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }

    @Override
    public String getMsg() {
        // 总结信息
        return StrUtil.format("导入用户结束：成功{}条，失败{}条；<br/>{}", validCount, invalidCount,
            msg);
    }
}
