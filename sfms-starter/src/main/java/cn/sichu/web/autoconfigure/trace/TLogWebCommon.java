package cn.sichu.web.autoconfigure.trace;

import com.yomahub.tlog.constant.TLogConstants;
import com.yomahub.tlog.core.rpc.TLogLabelBean;
import com.yomahub.tlog.core.rpc.TLogRPCHandler;
import jakarta.servlet.http.HttpServletRequest;

/**
 * TLog Web 通用拦截器
 *
 * <p>
 * 重写 TLog 配置以适配 Spring Boot 3.x
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class TLogWebCommon extends TLogRPCHandler {

    private static volatile TLogWebCommon tLogWebCommon;

    public static TLogWebCommon loadInstance() {
        if (tLogWebCommon == null) {
            synchronized (TLogWebCommon.class) {
                if (tLogWebCommon == null) {
                    tLogWebCommon = new TLogWebCommon();
                }
            }
        }
        return tLogWebCommon;
    }

    public void preHandle(HttpServletRequest request) {
        String traceId = request.getHeader(TLogConstants.TLOG_TRACE_KEY);
        String spanId = request.getHeader(TLogConstants.TLOG_SPANID_KEY);
        String preIvkApp = request.getHeader(TLogConstants.PRE_IVK_APP_KEY);
        String preIvkHost = request.getHeader(TLogConstants.PRE_IVK_APP_HOST);
        String preIp = request.getHeader(TLogConstants.PRE_IP_KEY);
        TLogLabelBean labelBean = new TLogLabelBean(preIvkApp, preIvkHost, preIp, traceId, spanId);
        processProviderSide(labelBean);
    }

    public void afterCompletion() {
        cleanThreadLocal();
    }
}
