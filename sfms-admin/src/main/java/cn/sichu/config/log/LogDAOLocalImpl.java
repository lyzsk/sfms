package cn.sichu.config.log;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import cn.sichu.auth.model.req.AccountLoginReq;
import cn.sichu.constant.SysConstants;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.DateUtils;
import cn.sichu.core.utils.ExceptionUtils;
import cn.sichu.core.utils.StrUtils;
import cn.sichu.log.dao.LogDAO;
import cn.sichu.log.model.LogRecord;
import cn.sichu.log.model.LogRequest;
import cn.sichu.log.model.LogResponse;
import cn.sichu.system.enums.LogStatusEnum;
import cn.sichu.system.mapper.LogMapper;
import cn.sichu.system.model.entity.LogDO;
import cn.sichu.system.service.IUserService;
import cn.sichu.web.autoconfigure.trace.TraceProperties;
import cn.sichu.web.model.R;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 日志持久层接口本地实现类
 *
 * @author sichu huang
 * @date 2024/10/13
 **/
@RequiredArgsConstructor
public class LogDAOLocalImpl implements LogDAO {

    private final IUserService userService;
    private final LogMapper logMapper;
    private final TraceProperties traceProperties;

    @Async
    @Override
    public void add(LogRecord logRecord) throws ParseException {
        LogDO logDO = new LogDO();
        // 设置请求信息
        LogRequest logRequest = logRecord.getRequest();
        this.setRequest(logDO, logRequest);
        // 设置响应信息
        LogResponse logResponse = logRecord.getResponse();
        this.setResponse(logDO, logResponse);
        // 设置基本信息
        logDO.setDescription(logRecord.getDescription());
        logDO.setModule(StrUtils.blankToDefault(logRecord.getModule(), null,
            m -> m.replace("API", StringConstants.EMPTY).trim()));
        logDO.setTimeTaken(logRecord.getTimeTaken().toMillis());
        logDO.setCreateTime(DateUtils.parseMillisecond(Date.from(logRecord.getTimestamp())));
        // 设置操作人
        this.setCreateUser(logDO, logRequest, logResponse);
        logMapper.insert(logDO);
    }

    /**
     * 设置请求信息
     *
     * @param logDO      日志信息
     * @param logRequest 请求信息
     * @author sichu huang
     * @date 2024/10/13
     **/
    private void setRequest(LogDO logDO, LogRequest logRequest) {
        logDO.setRequestMethod(logRequest.getMethod());
        logDO.setRequestUrl(logRequest.getUrl().toString());
        logDO.setRequestHeaders(JSONUtil.toJsonStr(logRequest.getHeaders()));
        logDO.setRequestBody(logRequest.getBody());
        logDO.setIp(logRequest.getIp());
        logDO.setAddress(logRequest.getAddress());
        logDO.setBrowser(logRequest.getBrowser());
        logDO.setOs(StrUtil.subBefore(logRequest.getOs(), " or", false));
    }

    /**
     * 设置响应信息
     *
     * @param logDO       日志信息
     * @param logResponse 响应信息
     * @author sichu huang
     * @date 2024/10/13
     **/
    private void setResponse(LogDO logDO, LogResponse logResponse) {
        Map<String, String> responseHeaders = logResponse.getHeaders();
        logDO.setResponseHeaders(JSONUtil.toJsonStr(responseHeaders));
        logDO.setTraceId(responseHeaders.get(traceProperties.getTraceIdName()));
        String responseBody = logResponse.getBody();
        logDO.setResponseBody(responseBody);
        // 状态
        Integer statusCode = logResponse.getStatus();
        logDO.setStatusCode(statusCode);
        logDO.setStatus(statusCode >= HttpStatus.HTTP_BAD_REQUEST ? LogStatusEnum.FAILURE :
            LogStatusEnum.SUCCESS);
        if (StrUtil.isNotBlank(responseBody)) {
            R result = JSONUtil.toBean(responseBody, R.class);
            if (!result.isSuccess()) {
                logDO.setStatus(LogStatusEnum.FAILURE);
                logDO.setErrorMsg(result.getMsg());
            }
        }
    }

    /**
     * 设置操作人
     *
     * @param logDO       日志信息
     * @param logRequest  请求信息
     * @param logResponse 响应信息
     * @author sichu huang
     * @date 2024/10/13
     **/
    private void setCreateUser(LogDO logDO, LogRequest logRequest, LogResponse logResponse) {
        String requestUri = URLUtil.getPath(logDO.getRequestUrl());
        // 解析退出接口信息
        String responseBody = logResponse.getBody();
        if (requestUri.startsWith(SysConstants.LOGOUT_URI) && StrUtil.isNotBlank(responseBody)) {
            R result = JSONUtil.toBean(responseBody, R.class);
            logDO.setCreateUser(Convert.toLong(result.getData(), null));
            return;
        }
        // 解析登录接口信息
        if (requestUri.startsWith(SysConstants.LOGIN_URI) && LogStatusEnum.SUCCESS.equals(
            logDO.getStatus())) {
            String requestBody = logRequest.getBody();
            AccountLoginReq loginReq = JSONUtil.toBean(requestBody, AccountLoginReq.class);
            logDO.setCreateUser(ExceptionUtils.exToNull(
                () -> userService.getByUsername(loginReq.getUsername()).getId()));
            return;
        }
        // 解析 Token 信息
        Map<String, String> requestHeaders = logRequest.getHeaders();
        String headerName = HttpHeaders.AUTHORIZATION;
        boolean isContainsAuthHeader = CollUtil.containsAny(requestHeaders.keySet(),
            Set.of(headerName, headerName.toLowerCase()));
        if (MapUtil.isNotEmpty(requestHeaders) && isContainsAuthHeader) {
            String authorization = requestHeaders.getOrDefault(headerName,
                requestHeaders.get(headerName.toLowerCase()));
            String token = authorization.replace(
                SaManager.getConfig().getTokenPrefix() + StringConstants.SPACE,
                StringConstants.EMPTY);
            logDO.setCreateUser(Convert.toLong(StpUtil.getLoginIdByToken(token)));
        }
    }
}
