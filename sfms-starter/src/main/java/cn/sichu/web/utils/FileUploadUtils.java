package cn.sichu.web.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class FileUploadUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    private FileUploadUtils() {
    }

    /**
     * 上传
     *
     * @param multipartFile          源文件对象
     * @param filePath               文件路径
     * @param isKeepOriginalFilename 是否保留原文件名
     * @return java.io.File 目标文件对象
     * @author sichu huang
     * @date 2024/10/11
     **/
    public static File upload(MultipartFile multipartFile, String filePath,
        boolean isKeepOriginalFilename) {
        String originalFilename = multipartFile.getOriginalFilename();
        String extensionName = FileNameUtil.extName(originalFilename);
        String fileName;
        if (isKeepOriginalFilename) {
            fileName = "%s-%s.%s".formatted(FileNameUtil.getPrefix(originalFilename),
                DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN), extensionName);
        } else {
            fileName = "%s.%s".formatted(IdUtil.fastSimpleUUID(), extensionName);
        }
        try {
            String pathname = filePath + fileName;
            File dest = new File(pathname).getCanonicalFile();
            // 如果父路径不存在，自动创建
            if (!dest.getParentFile().exists() && (!dest.getParentFile().mkdirs())) {
                log.error("Create upload file parent path failed.");
            }
            // 文件写入
            multipartFile.transferTo(dest);
            return dest;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 下载
     *
     * @param response 响应对象
     * @param file     文件
     * @author sichu huang
     * @date 2024/10/11
     **/
    public static void download(HttpServletResponse response, File file) throws IOException {
        download(response, new FileInputStream(file), file.getName());
    }

    /**
     * 下载
     *
     * @param response    响应对象
     * @param inputStream 文件流
     * @param fileName    文件名
     * @author sichu huang
     * @date 2024/10/11
     **/
    public static void download(HttpServletResponse response, InputStream inputStream,
        String fileName) throws IOException {
        byte[] bytes = IoUtil.readBytes(inputStream);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setContentLength(bytes.length);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + URLUtil.encode(fileName));
        IoUtil.write(response.getOutputStream(), true, bytes);
    }
}
