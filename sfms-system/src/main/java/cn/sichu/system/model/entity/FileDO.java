package cn.sichu.system.model.entity;

import cn.hutool.core.util.StrUtil;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.StrUtils;
import cn.sichu.crud.mp.model.entity.BaseDO;
import cn.sichu.system.enums.FileTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.x.file.storage.core.FileInfo;

import java.io.Serial;

/**
 * @author sichu huang
 * @date 2024/10/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_file")
public class FileDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     **/
    private String name;

    /**
     * 大小（字节）
     **/
    private Long size;

    /**
     * URL
     **/
    private String url;

    /**
     * 扩展名
     **/
    private String extension;

    /**
     * 类型
     **/
    private FileTypeEnum type;

    /**
     * 缩略图大小（字节)
     **/
    private Long thumbnailSize;

    /**
     * 缩略图URL
     **/
    private String thumbnailUrl;

    /**
     * 存储 ID
     **/
    private Long storageId;

    /**
     * 转换为 X-File-Storage 文件信息对象
     *
     * @param storageCode 存储编码
     * @return X-File-Storage 文件信息对象
     **/
    public FileInfo toFileInfo(String storageCode) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(this.url);
        fileInfo.setSize(this.size);
        fileInfo.setFilename(StrUtil.contains(this.url, StringConstants.SLASH) ?
            StrUtil.subAfter(this.url, StringConstants.SLASH, true) : this.url);
        fileInfo.setOriginalFilename(StrUtils.blankToDefault(this.extension, this.name,
            ex -> this.name + StringConstants.DOT + ex));
        fileInfo.setBasePath(StringConstants.EMPTY);
        fileInfo.setPath(
            StrUtil.subBefore(this.url, StringConstants.SLASH, true) + StringConstants.SLASH);
        fileInfo.setExt(this.extension);
        fileInfo.setPlatform(storageCode);
        fileInfo.setThUrl(this.thumbnailUrl);
        fileInfo.setThFilename(StrUtil.contains(this.thumbnailUrl, StringConstants.SLASH) ?
            StrUtil.subAfter(this.thumbnailUrl, StringConstants.SLASH, true) : this.thumbnailUrl);
        fileInfo.setThSize(this.thumbnailSize);
        return fileInfo;
    }
}
