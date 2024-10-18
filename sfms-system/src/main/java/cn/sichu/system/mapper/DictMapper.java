package cn.sichu.system.mapper;

import cn.sichu.system.model.entity.Dict;
import cn.sichu.system.model.query.DictPageQuery;
import cn.sichu.system.model.vo.DictPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sichu huang
 * @since 2024/10/16 23:19
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 字典分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.DictPageVO>
     * @author sichu huang
     * @since 2024/10/16 23:19:35
     */
    Page<DictPageVO> getDictPage(Page<DictPageVO> page, DictPageQuery queryParams);
}
