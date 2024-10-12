package cn.sichu.config.websocket;

import cn.sichu.messaging.websocket.core.WebSocketClientService;
import cn.sichu.model.dto.LoginUser;
import cn.sichu.utils.helper.LoginHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 当前登录用户 Provider
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Component
public class WebSocketClientServiceImpl implements WebSocketClientService {

    @Override
    public String getClientId(ServletServerHttpRequest request) {
        HttpServletRequest servletRequest = request.getServletRequest();
        String token = servletRequest.getParameter("token");
        LoginUser loginUser = LoginHelper.getLoginUser(token);
        return loginUser.getToken();
    }
}
