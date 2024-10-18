package cn.sichu.system.service;

import cn.sichu.model.Option;
import cn.sichu.system.enums.ContactType;
import cn.sichu.system.model.dto.UserAuthInfo;
import cn.sichu.system.model.dto.UserExportDTO;
import cn.sichu.system.model.entity.User;
import cn.sichu.system.model.form.*;
import cn.sichu.system.model.query.UserPageQuery;
import cn.sichu.system.model.vo.UserInfoVO;
import cn.sichu.system.model.vo.UserPageVO;
import cn.sichu.system.model.vo.UserProfileVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author sichu huang
 * @since 2024/10/17 00:04:37
 */
public interface UserService extends IService<User> {

    /**
     * 用户分页列表
     *
     * @param queryParams queryParams
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.model.vo.UserPageVO>
     * @author sichu huang
     * @since 2024/10/17 00:04:59
     */
    IPage<UserPageVO> listPagedUsers(UserPageQuery queryParams);

    /**
     * 获取用户表单数据
     *
     * @param userId userId
     * @return cn.sichu.model.form.UserForm
     * @author sichu huang
     * @since 2024/10/17 00:05:09
     */
    UserForm getUserFormData(Long userId);

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:05:17
     */
    boolean saveUser(UserForm userForm);

    /**
     * 修改用户
     *
     * @param userId   用户ID
     * @param userForm 用户表单对象
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:05:27
     */
    boolean updateUser(Long userId, UserForm userForm);

    /**
     * 删除用户
     *
     * @param idsStr 用户ID，多个以英文逗号(,)分割
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:05:37
     */
    boolean deleteUsers(String idsStr);

    /**
     * 根据用户名获取认证信息
     *
     * @param username 用户名
     * @return cn.sichu.model.dto.UserAuthInfo
     * @author sichu huang
     * @since 2024/10/17 00:05:59
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * 获取导出用户列表
     *
     * @param queryParams 查询参数
     * @return java.util.List<cn.sichu.model.dto.UserExportDTO>
     * @author sichu huang
     * @since 2024/10/17 00:06:09
     */
    List<UserExportDTO> listExportUsers(UserPageQuery queryParams);

    /**
     * 获取登录用户信息
     *
     * @return cn.sichu.model.vo.UserInfoVO
     * @author sichu huang
     * @since 2024/10/17 00:06:18
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * 获取个人中心用户信息
     *
     * @param userId userId
     * @return cn.sichu.model.vo.UserProfileVO
     * @author sichu huang
     * @since 2024/10/17 00:06:28
     */
    UserProfileVO getUserProfile(Long userId);

    /**
     * 修改个人中心用户信息
     *
     * @param formData 表单数据
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:06:46
     */
    boolean updateUserProfile(UserProfileForm formData);

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param data   修改密码表单数据
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:06:55
     */
    boolean changePassword(Long userId, PasswordChangeForm data);

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 重置后的密码
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:07:04
     */
    boolean resetPassword(Long userId, String password);

    /**
     * 发送验证码
     *
     * @param contact 联系方式
     * @param type    联系方式类型
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:07:24
     */
    boolean sendVerificationCode(String contact, ContactType type);

    /**
     * 修改当前用户手机号
     *
     * @param data 表单数据
     * @return boolean
     * @author sichu huang
     * @since 2024/10/17 00:07:33
     */
    boolean bindMobile(MobileBindingForm data);

    /**
     * 修改当前用户邮箱
     *
     * @param data 表单数据
     * @return boolean 是否绑定成功
     * @author sichu huang
     * @since 2024/10/17 00:07:43
     */
    boolean bindEmail(EmailChangeForm data);

    /**
     * 获取用户选项列表
     *
     * @return java.util.List<cn.sichu.model.Option < java.lang.String>> 用户选项列表
     * @author sichu huang
     * @since 2024/10/17 00:08:02
     */
    List<Option<String>> listUserOptions();
}
