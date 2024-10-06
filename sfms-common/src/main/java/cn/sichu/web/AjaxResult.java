package cn.sichu.web;

import cn.sichu.constant.HttpStatus;
import cn.sichu.utils.StringUtil;

import java.util.HashMap;

/**
 * @author sichu huang
 * @date 2024/10/06
 **/
public class AjaxResult extends HashMap<String, Object> {
    public static final String CODE = "code";
    public static final String MSG = "message";
    public static final String DATA = "data";

    public AjaxResult() {
    }

    public AjaxResult(int code, String msg) {
        super.put(CODE, code);
        super.put(MSG, msg);
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE, code);
        super.put(MSG, msg);
        if (StringUtil.isNotNull(data)) {
            super.put(DATA, data);
        }
    }

    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    public static AjaxResult error() {
        return AjaxResult.error("操作成功");
    }

    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error(Object data) {
        return AjaxResult.error("操作成功", data);
    }

    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }
}
