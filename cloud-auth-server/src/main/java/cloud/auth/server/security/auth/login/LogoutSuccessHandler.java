package cloud.auth.server.security.auth.login;

import cloud.auth.server.security.common.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	private String targetUrl;

	public LogoutSuccessHandler(String targetUrl) {
		super();
		this.targetUrl = targetUrl;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (WebUtil.isAjax(request)) {

			response.setContentType("application/json");
			try (PrintWriter writer = response.getWriter();) {
//				writer.print(JsonUtil.toJson(RespBody.succeed()));
				writer.flush();
			}

		} else {
			setDefaultTargetUrl(targetUrl);
			super.onLogoutSuccess(request, response, authentication);
		}
	}

}
