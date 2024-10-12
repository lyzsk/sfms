package cn.sichu.system.mapper;

import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.DictItemDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface DictItemMapper extends BaseMapper<DictItemDO> {

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return java.util.List<cn.sichu.crud.core.resp.LabelValueResp> 字典项列表
     * @author sichu huang
     * @date 2024/10/11
     **/
    List<LabelValueResp> listByDictCode(@Param("dictCode") String dictCode);
}
