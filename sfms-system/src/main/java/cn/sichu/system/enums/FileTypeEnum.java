package cn.sichu.system.enums;

import cn.hutool.core.util.StrUtil;
import cn.sichu.core.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 文件类型枚举
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
@RequiredArgsConstructor
public enum FileTypeEnum implements BaseEnum<Integer> {
    /**
     * 其他
     **/
    UNKNOWN(1, "其他", Collections.emptyList()),

    /**
     * 图片
     **/
    IMAGE(2, "图片",
        List.of("jpg", "jpeg", "png", "gif", "bmp", "webp", "ico", "psd", "tiff", "dwg", "jxr",
            "apng", "xcf")),
    /**
     * 文档
     **/
    DOC(3, "文档", List.of("txt", "pdf", "doc", "xls", "ppt", "docx", "xlsx", "pptx")),

    /**
     * 视频
     **/
    VIDEO(4, "视频",
        List.of("mp4", "avi", "mkv", "flv", "webm", "wmv", "m4v", "mov", "mpg", "rmvb", "3gp")),

    /**
     * 音频
     **/
    AUDIO(5, "音频",
        List.of("mp3", "flac", "wav", "ogg", "midi", "m4a", "aac", "amr", "ac3", "aiff")),
    ;

    private final Integer value;
    private final String description;
    private final List<String> extensions;

    /**
     * 根据扩展名查询
     *
     * @param extension 扩展名
     * @return cn.sichu.system.enums.FileTypeEnum 文件类型
     * @author sichu huang
     * @date 2024/10/11
     **/
    public static FileTypeEnum getByExtension(String extension) {
        return Arrays.stream(FileTypeEnum.values())
            .filter(t -> t.getExtensions().contains(StrUtil.emptyIfNull(extension).toLowerCase()))
            .findFirst().orElse(FileTypeEnum.UNKNOWN);
    }
}
