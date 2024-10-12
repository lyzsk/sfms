package cn.sichu.system.model.resp.log;

import cn.sichu.file.excel.converter.ExcelBaseEnumConverter;
import cn.sichu.system.enums.LogStatusEnum;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志导出信息
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Data
@ExcelIgnoreUnannotated
@Schema(description = "登录日志导出信息")
public class LoginLogExportResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     **/
    @Schema(description = "ID", example = "1")
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 登录时间
     **/
    @Schema(description = "登录时间", example = "2023-08-08 08:08:08.000", type = "string")
    @ExcelProperty(value = "登录时间")
    private Date createTime;

    /**
     * 用户昵称
     **/
    @Schema(description = "用户昵称", example = "张三")
    @ExcelProperty(value = "用户昵称")
    private String createUserString;

    /**
     * 登录行为
     **/
    @Schema(description = "登录行为", example = "账号登录")
    @ExcelProperty(value = "登录行为")
    private String description;

    /**
     * 状态
     **/
    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class)
    private LogStatusEnum status;

    /**
     * 登录 IP
     **/
    @Schema(description = "登录 IP", example = "")
    @ExcelProperty(value = "登录 IP")
    private String ip;

    /**
     * 登录地点
     **/
    @Schema(description = "登录地点", example = "中国北京北京市")
    @ExcelProperty(value = "登录地点")
    private String address;

    /**
     * 浏览器
     **/
    @Schema(description = "浏览器", example = "Chrome 115.0.0.0")
    @ExcelProperty(value = "浏览器")
    private String browser;

    /**
     * 终端系统
     **/
    @Schema(description = "终端系统", example = "Windows 10")
    @ExcelProperty(value = "终端系统")
    private String os;
}
