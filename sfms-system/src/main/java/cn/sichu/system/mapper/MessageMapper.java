package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.MessageDO;
import cn.sichu.system.model.resp.MessageResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 消息 Mapper
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface MessageMapper extends BaseMapper<MessageDO> {

    /**
     * 分页查询列表
     *
     * @param page         分页查询条件
     * @param queryWrapper 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.system.model.resp.MessageResp> 分页信息
     * @author sichu huang
     * @date 2024/10/12
     **/
    IPage<MessageResp> selectPageByUserId(@Param("page") IPage<Object> page,
        @Param(Constants.WRAPPER) QueryWrapper<MessageDO> queryWrapper);
}