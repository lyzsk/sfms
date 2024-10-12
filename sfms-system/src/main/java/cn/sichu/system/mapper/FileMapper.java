package cn.sichu.system.mapper;

import cn.sichu.data.mp.base.BaseMapper;
import cn.sichu.system.model.entity.FileDO;
import cn.sichu.system.model.resp.FileStatisticsResp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文件 Mapper
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface FileMapper extends BaseMapper<FileDO> {

    /**
     * 查询文件资源统计信息
     *
     * @return java.util.List<cn.sichu.system.model.resp.FileStatisticsResp> 文件资源统计信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    @Select("SELECT type, COUNT(1) number, SUM(size) size FROM t_sys_file " + "GROUP BY type")
    List<FileStatisticsResp> statistics();
}
