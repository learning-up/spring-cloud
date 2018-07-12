package cloud.auth.server.security.auth.login;

import cloud.auth.server.security.common.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

//@Component
public class LoginSuccessHandler extends LoginUrlAuthenticationEntryPoint {

	public LoginSuccessHandler(String loginFormUrl) {
		super(loginFormUrl);
	}

	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {
		if (WebUtil.isAjax(request)) {
			// 对于ajax请求不重定向 而是返回错误代码
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
		} else {
			super.commence(request, response, authException);
		}
	}

}
