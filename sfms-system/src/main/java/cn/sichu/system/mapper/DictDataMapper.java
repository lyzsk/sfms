package cn.sichu.system.mapper;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.DictData;
import cn.sichu.system.model.query.DictDataPageQuery;
import cn.sichu.system.model.vo.DictDataPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sichu huang
 * @since 2024/10/16 23:18
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * 字典数据分页列表
     *
     * @param page        page
     * @param queryParams queryParams
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.DictDataPageVO>
     * @author sichu huang
     * @since 2024/10/16 23:18:59
     */
    Page<DictDataPageVO> getDictDataPage(Page<DictDataPageVO> page, DictDataPageQuery queryParams);

    /**
     * 根据字典编码获取字典数据列表
     *
     * @param dictCode dictCode
     * @return cn.sichu.model.Option
     * @author sichu huang
     * @since 2024/10/16 23:18:50
     */
    Option listDictDataByDictCode(String dictCode);
}
