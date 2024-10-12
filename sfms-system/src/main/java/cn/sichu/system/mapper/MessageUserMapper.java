package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.MessageUserDO;
import org.apache.ibatis.annotations.Param;

/**
 * 消息和用户 Mapper
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface MessageUserMapper extends BaseMapper<MessageUserDO> {

    /**
     * 根据用户 ID 和消息类型查询未读消息数量
     *
     * @param userId 用户 ID
     * @param type   消息类型
     * @return java.lang.Long 未读消息信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    Long selectUnreadCountByUserIdAndType(@Param("userId") Long userId,
        @Param("type") Integer type);
}