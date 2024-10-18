package cn.sichu.system.mapper;

import cn.sichu.annotation.DataPermission;
import cn.sichu.system.model.bo.UserBO;
import cn.sichu.system.model.dto.UserAuthInfo;
import cn.sichu.system.model.dto.UserExportDTO;
import cn.sichu.system.model.entity.User;
import cn.sichu.system.model.form.UserForm;
import cn.sichu.system.model.query.UserPageQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/16 23:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户分页列表
     *
     * @param page        page
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.bo.UserBO>
     * @author sichu huang
     * @since 2024/10/16 23:25:32
     */
    @DataPermission(deptAlias = "u")
    Page<UserBO> listPagedUsers(Page<UserBO> page, UserPageQuery queryParams);

    /**
     * 获取用户表单详情
     *
     * @param userId 用户ID
     * @return cn.sichu.model.form.UserForm
     * @author sichu huang
     * @since 2024/10/16 23:25:47
     */
    UserForm getUserFormData(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username username
     * @return cn.sichu.model.dto.UserAuthInfo
     * @author sichu huang
     * @since 2024/10/16 23:25:58
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * 获取导出用户列表
     *
     * @param queryParams queryParams
     * @return java.util.List<cn.sichu.model.dto.UserExportDTO>
     * @author sichu huang
     * @since 2024/10/16 23:26:08
     */
    @DataPermission(deptAlias = "u")
    List<UserExportDTO> listExportUsers(UserPageQuery queryParams);

    /**
     * 获取用户个人中心信息
     *
     * @param userId 用户ID
     * @return cn.sichu.model.bo.UserBO
     * @author sichu huang
     * @since 2024/10/16 23:26:17
     */
    UserBO getUserProfile(Long userId);
}
