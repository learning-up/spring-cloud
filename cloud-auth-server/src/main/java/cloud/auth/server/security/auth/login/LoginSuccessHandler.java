package cloud.auth.server.security.auth.login;

import cloud.auth.server.security.model.UserContext;
import cloud.auth.server.security.model.token.AccessToken;
import cloud.auth.server.security.model.token.Token;
import cloud.auth.server.security.model.token.TokenFactory;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 认证成功处理程序
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired private ObjectMapper mapper;
    @Autowired private TokenFactory tokenFactory;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        JSONObject tokenMap = tokenFactory.tokenWrapper(userContext);

        response.setStatus(HttpStatus.OK.value());

        String redirect = request.getParameter("redirect");
        if(!StringUtils.isBlank(redirect) && !"null".equals(redirect)){
            response.sendRedirect(redirect);
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), tokenMap);
        }

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null)
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
