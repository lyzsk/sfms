package cn.sichu.system.controller;

import cn.sichu.annotation.Log;
import cn.sichu.annotation.RepeatSubmit;
import cn.sichu.enums.LogModuleEnum;
import cn.sichu.model.Option;
import cn.sichu.result.PageResult;
import cn.sichu.result.Result;
import cn.sichu.system.core.utils.SecurityUtils;
import cn.sichu.system.enums.ContactType;
import cn.sichu.system.listener.UserImportListener;
import cn.sichu.system.model.dto.UserExportDTO;
import cn.sichu.system.model.dto.UserImportDTO;
import cn.sichu.system.model.entity.User;
import cn.sichu.system.model.form.*;
import cn.sichu.system.model.query.UserPageQuery;
import cn.sichu.system.model.vo.UserInfoVO;
import cn.sichu.system.model.vo.UserPageVO;
import cn.sichu.system.model.vo.UserProfileVO;
import cn.sichu.system.service.UserService;
import cn.sichu.utils.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/17 17:01
 */
@Tag(name = "02.用户接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户分页列表")
    @GetMapping("/page")
    @Log(value = "用户分页列表", module = LogModuleEnum.USER)
    public PageResult<UserPageVO> listPagedUsers(UserPageQuery queryParams) {
        IPage<UserPageVO> result = userService.listPagedUsers(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @RepeatSubmit
    public Result<?> saveUser(@RequestBody @Valid UserForm userForm) {
        boolean result = userService.saveUser(userForm);
        return Result.judge(result);
    }

    @Operation(summary = "用户表单数据")
    @GetMapping("/{userId}/form")
    public Result<UserForm> getUserForm(
        @Parameter(description = "用户ID") @PathVariable Long userId) {
        UserForm formData = userService.getUserFormData(userId);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户")
    @PutMapping(value = "/{userId}")
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    public Result<?> updateUser(@Parameter(description = "用户ID") @PathVariable Long userId,
        @RequestBody @Validated UserForm userForm) {
        boolean result = userService.updateUser(userId, userForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:delete')")
    public Result<Void> deleteUsers(
        @Parameter(description = "用户ID，多个以英文逗号(,)分割") @PathVariable String ids) {
        boolean result = userService.deleteUsers(ids);
        return Result.judge(result);
    }

    @Operation(summary = "修改用户状态")
    @PatchMapping(value = "/{userId}/status")
    public Result<Void> updateUserStatus(
        @Parameter(description = "用户ID") @PathVariable Long userId,
        @Parameter(description = "用户状态(1:启用;0:禁用)") @RequestParam Integer status) {
        boolean result = userService.update(
            new LambdaUpdateWrapper<User>().eq(User::getId, userId).set(User::getStatus, status));
        return Result.judge(result);
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/me")
    public Result<UserInfoVO> getCurrentUserInfo() {
        UserInfoVO userInfoVO = userService.getCurrentUserInfo();
        return Result.success(userInfoVO);
    }

    @Operation(summary = "用户导入模板下载")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "用户导入模板.xlsx";
        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        String fileClassPath = "templates" + File.separator + "excel" + File.separator + fileName;
        InputStream inputStream =
            this.getClass().getClassLoader().getResourceAsStream(fileClassPath);
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

        excelWriter.finish();
    }

    @Operation(summary = "导入用户")
    @PostMapping("/import")
    public Result<String> importUsers(MultipartFile file) throws IOException {
        UserImportListener listener = new UserImportListener();
        String msg = ExcelUtils.importExcel(file.getInputStream(), UserImportDTO.class, listener);
        return Result.success(msg);
    }

    @Operation(summary = "导出用户")
    @GetMapping("/export")
    public void exportUsers(UserPageQuery queryParams, HttpServletResponse response)
        throws IOException {
        String fileName = "用户列表.xlsx";
        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        List<UserExportDTO> exportUserList = userService.listExportUsers(queryParams);
        EasyExcel.write(response.getOutputStream(), UserExportDTO.class).sheet("用户列表")
            .doWrite(exportUserList);
    }

    @Operation(summary = "获取个人中心用户信息")
    @GetMapping("/profile")
    public Result<UserProfileVO> getUserProfile() {
        Long userId = SecurityUtils.getUserId();
        UserProfileVO userProfile = userService.getUserProfile(userId);
        return Result.success(userProfile);
    }

    @Operation(summary = "修改个人中心用户信息")
    @PutMapping("/profile")
    public Result<?> updateUserProfile(@RequestBody UserProfileForm formData) {
        boolean result = userService.updateUserProfile(formData);
        return Result.judge(result);
    }

    @Operation(summary = "重置用户密码")
    @PutMapping(value = "/{userId}/password/reset")
    @PreAuthorize("@ss.hasPerm('sys:user:password:reset')")
    public Result<?> resetPassword(@Parameter(description = "用户ID") @PathVariable Long userId,
        @RequestParam String password) {
        boolean result = userService.resetPassword(userId, password);
        return Result.judge(result);
    }

    @Operation(summary = "修改密码")
    @PutMapping(value = "/password")
    public Result<?> changePassword(@RequestBody PasswordChangeForm data) {
        Long currUserId = SecurityUtils.getUserId();
        boolean result = userService.changePassword(currUserId, data);
        return Result.judge(result);
    }

    @Operation(summary = "发送短信/邮箱验证码")
    @PostMapping(value = "/send-verification-code")
    public Result<?> sendVerificationCode(
        @Parameter(description = "联系方式（手机号码或邮箱地址）", required = true) @RequestParam
        String contact,
        @Parameter(description = "联系方式类型（Mobile或Email）", required = true) @RequestParam
        ContactType contactType) {
        boolean result = userService.sendVerificationCode(contact, contactType);
        return Result.judge(result);
    }

    @Operation(summary = "绑定个人中心用户手机号")
    @PutMapping(value = "/mobile")
    public Result<?> bindMobile(@RequestBody @Validated MobileBindingForm data) {
        boolean result = userService.bindMobile(data);
        return Result.judge(result);
    }

    @Operation(summary = "绑定个人中心用户邮箱")
    @PutMapping(value = "/email")
    public Result<?> bindEmail(@RequestBody @Validated EmailChangeForm data) {
        boolean result = userService.bindEmail(data);
        return Result.judge(result);
    }

    @Operation(summary = "用户下拉选项")
    @GetMapping("/options")
    public Result<List<Option<String>>> listUserOptions() {
        List<Option<String>> list = userService.listUserOptions();
        return Result.success(list);
    }
}