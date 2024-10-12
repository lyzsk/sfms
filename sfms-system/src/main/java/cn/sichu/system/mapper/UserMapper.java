package cn.sichu.system.mapper;

import cn.sichu.config.mybatis.DataPermissionMapper;
import cn.sichu.data.mp.datapermission.DataPermission;
import cn.sichu.security.crypto.annotation.FieldEncrypt;
import cn.sichu.system.model.entity.UserDO;
import cn.sichu.system.model.resp.UserDetailResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface UserMapper extends DataPermissionMapper<UserDO> {

    /**
     * 分页查询列表
     *
     * @param page         分页条件
     * @param queryWrapper 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.system.model.resp.UserDetailResp> 分页列表信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    @DataPermission(tableAlias = "t1")
    IPage<UserDetailResp> selectUserPage(@Param("page") IPage<UserDO> page,
        @Param(Constants.WRAPPER) QueryWrapper<UserDO> queryWrapper);

    /**
     * 查询列表
     *
     * @param queryWrapper 查询条件
     * @return java.util.List<cn.sichu.system.model.resp.UserDetailResp> 列表信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    @DataPermission(tableAlias = "t1")
    List<UserDetailResp> selectUserList(
        @Param(Constants.WRAPPER) QueryWrapper<UserDO> queryWrapper);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return cn.sichu.system.model.entity.UserDO 用户信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Select("SELECT * FROM t_sys_user WHERE username = #{username}")
    UserDO selectByUsername(@Param("username") String username);

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return cn.sichu.system.model.entity.UserDO 用户信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Select("SELECT * FROM t_sys_user WHERE phone = #{phone}")
    UserDO selectByPhone(@FieldEncrypt @Param("phone") String phone);

    /**
     * 根据邮箱查询
     *
     * @param email 邮箱
     * @return cn.sichu.system.model.entity.UserDO 用户信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Select("SELECT * FROM t_sys_user WHERE email = #{email}")
    UserDO selectByEmail(@FieldEncrypt @Param("email") String email);

    /**
     * 根据 ID 查询昵称
     *
     * @param id ID
     * @return java.lang.String 昵称
     * @author sichu huang
     * @date 2024/10/12
     **/
    @Select("SELECT nickname FROM t_sys_user WHERE id = #{id}")
    String selectNicknameById(@Param("id") Long id);

    /**
     * 根据邮箱查询数量
     *
     * @param email 邮箱
     * @param id    id
     * @return java.lang.Long 用户数量
     * @author sichu huang
     * @date 2024/10/12
     **/
    Long selectCountByEmail(@FieldEncrypt @Param("email") String email, @Param("id") Long id);

    /**
     * 根据手机号查询数量
     *
     * @param phone 手机号
     * @param id    id
     * @return java.lang.Long
     * @author sichu huang
     * @date 2024/10/12
     **/
    Long selectCountByPhone(@FieldEncrypt @Param("phone") String phone, @Param("id") Long id);
}
