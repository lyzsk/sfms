package cn.sichu.system.config.file;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.DateUtils;
import cn.sichu.system.enums.FileTypeEnum;
import cn.sichu.system.mapper.FileMapper;
import cn.sichu.system.mapper.StorageMapper;
import cn.sichu.system.model.entity.FileDO;
import cn.sichu.system.model.entity.StorageDO;
import cn.sichu.utils.helper.LoginHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

/**
 * 文件记录实现类
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class FileRecorderImpl implements FileRecorder {

    private final FileMapper fileMapper;
    private final StorageMapper storageMapper;

    @Override
    public boolean save(FileInfo fileInfo) {
        FileDO file = new FileDO();
        String originalFilename = EscapeUtil.unescape(fileInfo.getOriginalFilename());
        file.setName(StrUtil.contains(originalFilename, StringConstants.DOT) ?
            StrUtil.subBefore(originalFilename, StringConstants.DOT, true) : originalFilename);
        file.setUrl(fileInfo.getUrl());
        file.setSize(fileInfo.getSize());
        file.setExtension(fileInfo.getExt());
        file.setType(FileTypeEnum.getByExtension(file.getExtension()));
        file.setThumbnailUrl(fileInfo.getThUrl());
        file.setThumbnailSize(fileInfo.getThSize());
        StorageDO storage =
            (StorageDO)fileInfo.getAttr().get(ClassUtil.getClassName(StorageDO.class, false));
        file.setStorageId(storage.getId());
        try {
            file.setCreateTime(DateUtils.parseMillisecond(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        file.setUpdateUser(LoginHelper.getUserId());
        file.setUpdateTime(file.getCreateTime());
        fileMapper.insert(file);
        return true;
    }

    @Override
    public FileInfo getByUrl(String url) {
        FileDO file = this.getFileByUrl(url);
        if (null == file) {
            return null;
        }
        return file.toFileInfo(
            storageMapper.lambdaQuery().eq(StorageDO::getId, file.getStorageId()).one().getCode());
    }

    @Override
    public boolean delete(String url) {
        FileDO file = this.getFileByUrl(url);
        return fileMapper.lambdaUpdate().eq(FileDO::getUrl, file.getUrl()).remove();
    }

    @Override
    public void update(FileInfo fileInfo) {
        /* 不使用分片功能则无需重写 */
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        /* 不使用分片功能则无需重写 */
    }

    @Override
    public void deleteFilePartByUploadId(String s) {
        /* 不使用分片功能则无需重写 */
    }

    /**
     * 根据 URL 查询文件
     *
     * @param url URL
     * @return 文件信息
     */
    private FileDO getFileByUrl(String url) {
        Optional<FileDO> fileOptional = fileMapper.lambdaQuery().eq(FileDO::getUrl, url).oneOpt();
        return fileOptional.orElseGet(() -> fileMapper.lambdaQuery()
            .likeLeft(FileDO::getUrl, StrUtil.subAfter(url, StringConstants.SLASH, true)).oneOpt()
            .orElse(null));
    }
}