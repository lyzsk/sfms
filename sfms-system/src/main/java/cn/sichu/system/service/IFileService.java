package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.FileDO;
import cn.sichu.system.model.query.FileQuery;
import cn.sichu.system.model.req.FileReq;
import cn.sichu.system.model.resp.FileResp;
import cn.sichu.system.model.resp.FileStatisticsResp;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IFileService
    extends BaseService<FileResp, FileResp, FileQuery, FileReq>, IService<FileDO> {
    /**
     * 上传到默认存储
     *
     * @param file 文件信息
     * @return org.dromara.x.file.storage.core.FileInfo 文件信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    default FileInfo upload(MultipartFile file) {
        return upload(file, null);
    }

    /**
     * 上传到指定存储
     *
     * @param file        文件信息
     * @param storageCode 存储编码
     * @return org.dromara.x.file.storage.core.FileInfo 文件信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    FileInfo upload(MultipartFile file, String storageCode);

    /**
     * 根据存储ID列表 查询
     *
     * @param storageIds 存储ID列表
     * @return java.lang.Long 文件数量
     * @author sichu huang
     * @date 2024/10/11
     **/
    Long countByStorageIds(List<Long> storageIds);

    /**
     * 查询文件资源统计信息
     *
     * @return cn.sichu.system.model.resp.FileStatisticsResp 资源统计信息
     * @author sichu huang
     * @date 2024/10/11
     **/
    FileStatisticsResp statistics();
}
