package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.UserDO;
import cn.sichu.system.model.query.UserQuery;
import cn.sichu.system.model.req.*;
import cn.sichu.system.model.resp.UserDetailResp;
import cn.sichu.system.model.resp.UserImportParseResp;
import cn.sichu.system.model.resp.UserImportResp;
import cn.sichu.system.model.resp.UserResp;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 用户业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IUserService
    extends BaseService<UserResp, UserDetailResp, UserQuery, UserReq>, IService<UserDO> {
    /**
     * 新增
     *
     * @param user 用户信息
     * @return java.lang.Long ID
     * @author sichu huang
     * @date 2024/10/10
     **/
    Long add(UserDO user);

    /**
     * 重置密码
     *
     * @param req 重置信息
     * @param id  ID
     * @author sichu huang
     * @date 2024/10/11
     **/
    void resetPassword(UserPasswordResetReq req, Long id);

    /**
     * 修改角色
     *
     * @param updateReq 修改信息
     * @param id        ID
     */
    void updateRole(UserRoleUpdateReq updateReq, Long id);

    /**
     * 上传头像
     *
     * @param avatar 头像文件
     * @param id     ID
     * @return java.lang.String 新头像路径
     * @author sichu huang
     * @date 2024/10/11
     **/
    String updateAvatar(MultipartFile avatar, Long id) throws IOException;

    /**
     * 修改基础信息
     *
     * @param req 修改信息
     * @param id  ID
     * @author sichu huang
     * @date 2024/10/11
     **/
    void updateBasicInfo(UserBasicInfoUpdateReq req, Long id);

    /**
     * 修改密码
     *
     * @param oldPassword 当前密码
     * @param newPassword 新密码
     * @param id          ID
     * @author sichu huang
     * @date 2024/10/11
     **/
    void updatePassword(String oldPassword, String newPassword, Long id);

    /**
     * 修改手机号
     *
     * @param newPhone    新手机号
     * @param oldPassword 当前密码
     * @param id          ID
     * @author sichu huang
     * @date 2024/10/11
     **/
    void updatePhone(String newPhone, String oldPassword, Long id);

    /**
     * 修改邮箱
     *
     * @param newEmail    新邮箱
     * @param oldPassword 当前密码
     * @param id          ID
     * @author sichu huang
     * @date 2024/10/11
     **/
    void updateEmail(String newEmail, String oldPassword, Long id);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return cn.sichu.system.model.entity.UserDo 用户信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    UserDO getByUsername(String username);

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return cn.sichu.system.model.entity.UserDo 用户信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    UserDO getByPhone(String phone);

    /**
     * 根据邮箱查询
     *
     * @param email 邮箱
     * @return cn.sichu.system.model.entity.UserDo 用户信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    UserDO getByEmail(String email);

    /**
     * 根据部门ID列表查询
     *
     * @param deptIds 部门ID列表
     * @return java.lang.Long 用户数量
     * @author sichu huang
     * @date 2024/10/11
     **/
    Long countByDeptIds(List<Long> deptIds);

    /**
     * 下载用户导入模板
     *
     * @param response response
     * @author sichu huang
     * @date 2024/10/11
     **/
    void downloadImportUserTemplate(HttpServletResponse response) throws IOException;

    /**
     * 导入用户
     *
     * @param req req
     * @return cn.sichu.system.model.resp.UserImportResp
     * @author sichu huang
     * @date 2024/10/11
     **/
    UserImportResp importUser(UserImportReq req) throws ParseException;

    /**
     * 解析用户导入数据
     *
     * @param file 导入用户文件
     * @return cn.sichu.system.model.resp.UserImportParseResp 解析结果
     * @author sichu huang
     * @date 2024/10/11
     **/
    UserImportParseResp parseImportUser(MultipartFile file);
}
