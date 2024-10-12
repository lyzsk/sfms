package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.UserPasswordHistoryDo;
import org.apache.ibatis.annotations.Param;

/**
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface UserPasswordHistoryMapper extends BaseMapper<UserPasswordHistoryDo> {

    /**
     * 删除过期历史密码
     *
     * @param userId 用户ID
     * @param count  保留N个历史
     * @author sichu huang
     * @date 2024/10/12
     **/
    void deleteExpired(@Param("userId") Long userId, @Param("count") int count);
}
