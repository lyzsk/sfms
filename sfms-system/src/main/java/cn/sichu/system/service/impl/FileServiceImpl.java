package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.StrUtils;
import cn.sichu.core.utils.URLUtils;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.mp.service.impl.BaseServiceImpl;
import cn.sichu.system.enums.FileTypeEnum;
import cn.sichu.system.mapper.FileMapper;
import cn.sichu.system.model.entity.FileDO;
import cn.sichu.system.model.entity.StorageDO;
import cn.sichu.system.model.query.FileQuery;
import cn.sichu.system.model.req.FileReq;
import cn.sichu.system.model.resp.FileResp;
import cn.sichu.system.model.resp.FileStatisticsResp;
import cn.sichu.system.service.IFileService;
import cn.sichu.system.service.IStorageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.ProgressListener;
import org.dromara.x.file.storage.core.upload.UploadPretreatment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl
    extends BaseServiceImpl<FileMapper, FileDO, FileResp, FileResp, FileQuery, FileReq>
    implements IFileService {

    private final FileStorageService fileStorageService;
    @Resource
    private IStorageService storageService;

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<FileDO> fileList = baseMapper.lambdaQuery().in(FileDO::getId, ids).list();
        Map<Long, List<FileDO>> fileListGroup =
            fileList.stream().collect(Collectors.groupingBy(FileDO::getStorageId));
        for (Map.Entry<Long, List<FileDO>> entry : fileListGroup.entrySet()) {
            StorageDO storage = storageService.getById(entry.getKey());
            for (FileDO file : entry.getValue()) {
                FileInfo fileInfo = file.toFileInfo(storage.getCode());
                fileStorageService.delete(fileInfo);
            }
        }
    }

    @Override
    public FileInfo upload(MultipartFile file, String storageCode) {
        StorageDO storage;
        if (StrUtil.isBlank(storageCode)) {
            storage = storageService.getDefaultStorage();
            CheckUtils.throwIfNull(storage, "请先指定默认存储");
        } else {
            storage = storageService.getByCode(storageCode);
            CheckUtils.throwIfNotExists(storage, "StorageDo", "Code", storageCode);
        }
        LocalDate today = LocalDate.now();
        String path =
            today.getYear() + StringConstants.SLASH + today.getMonthValue() + StringConstants.SLASH
                + today.getDayOfMonth() + StringConstants.SLASH;
        UploadPretreatment uploadPretreatment =
            fileStorageService.of(file).setPlatform(storage.getCode())
                .putAttr(ClassUtil.getClassName(StorageDO.class, false), storage).setPath(path);
        // 图片文件生成缩略图
        if (FileTypeEnum.IMAGE.getExtensions()
            .contains(FileNameUtil.extName(file.getOriginalFilename()))) {
            uploadPretreatment.thumbnail(img -> img.size(100, 100));
        }
        uploadPretreatment.setProgressMonitor(new ProgressListener() {
            @Override
            public void start() {
                log.info("开始上传");
            }

            @Override
            public void progress(long progressSize, Long allSize) {
                log.info("已上传 [{}]，总大小 [{}]", progressSize, allSize);
            }

            @Override
            public void finish() {
                log.info("上传结束");
            }
        });
        // 处理本地存储文件 URL
        FileInfo fileInfo = uploadPretreatment.upload();
        String domain = StrUtil.appendIfMissing(storage.getDomain(), StringConstants.SLASH);
        fileInfo.setUrl(URLUtil.normalize(domain + fileInfo.getPath() + fileInfo.getFilename()));
        return fileInfo;
    }

    @Override
    public Long countByStorageIds(List<Long> storageIds) {
        return baseMapper.lambdaQuery().in(FileDO::getStorageId, storageIds).count();
    }

    @Override
    public FileStatisticsResp statistics() {
        FileStatisticsResp resp = new FileStatisticsResp();
        List<FileStatisticsResp> statisticsList = baseMapper.statistics();
        if (CollUtil.isEmpty(statisticsList)) {
            return resp;
        }
        resp.setData(statisticsList);
        resp.setSize(statisticsList.stream().mapToLong(FileStatisticsResp::getSize).sum());
        resp.setNumber(statisticsList.stream().mapToLong(FileStatisticsResp::getNumber).sum());
        return resp;
    }

    @Override
    protected void fill(Object obj) {
        super.fill(obj);
        if (obj instanceof FileResp fileResp && !URLUtils.isHttpUrl(fileResp.getUrl())) {
            StorageDO storage = storageService.getById(fileResp.getStorageId());
            String prefix = StrUtil.appendIfMissing(storage.getDomain(), StringConstants.SLASH);
            String url = URLUtil.normalize(prefix + fileResp.getUrl());
            fileResp.setUrl(url);
            String thumbnailUrl = StrUtils.blankToDefault(fileResp.getThumbnailUrl(), url,
                thUrl -> URLUtil.normalize(prefix + thUrl));
            fileResp.setThumbnailUrl(thumbnailUrl);
            fileResp.setStorageName("%s (%s)".formatted(storage.getName(), storage.getCode()));
        }
    }
}
