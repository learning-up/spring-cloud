package cloud.auth.server.security.auth.login;

import cloud.auth.server.security.common.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (WebUtil.isAjax(request)) {

			response.setContentType("application/json");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied!");
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}
