package cn.sichu.log.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.sichu.log.model.RecordableHttpResponse;
import cn.sichu.web.utils.ServletUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.util.Map;

/**
 * 可记录的 HTTP 响应信息适配器
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public final class RecordableServletHttpResponse implements RecordableHttpResponse {

    private final HttpServletResponse response;

    private final int status;

    public RecordableServletHttpResponse(HttpServletResponse response, int status) {
        this.response = response;
        this.status = status;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public Map<String, String> getHeaders() {
        return ServletUtils.getHeaderMap(response);
    }

    @Override
    public String getBody() {
        ContentCachingResponseWrapper wrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (null != wrapper) {
            String body = StrUtil.utf8Str(wrapper.getContentAsByteArray());
            return JSONUtil.isTypeJSON(body) ? body : null;
        }
        return null;
    }

    @Override
    public Map<String, Object> getParam() {
        String body = this.getBody();
        return CharSequenceUtil.isNotBlank(body) && JSONUtil.isTypeJSON(body) ?
            JSONUtil.toBean(body, Map.class) : null;
    }
}
