package cloud.auth.server.security.auth.token;

import cloud.auth.server.security.auth.AuthenticationToken;
import cloud.auth.server.security.auth.token.extractor.TokenExtractor;
import cloud.auth.server.security.common.Constants;
import cloud.auth.server.security.model.token.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 检查访问令牌在X-Authorization头
 * 调用执行Token的验证
 */
public class TokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    
    @Autowired
    public TokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler, 
            TokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
//        Cookie cookie = Arrays.stream(request.getCookies()).filter(t -> t.getName().equals("X-COOKIE")).findFirst().get();
        String tokenPayload = request.getHeader(Constants.TOKEN_HEADER_PARAM);
        AccessToken token = new AccessToken(tokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new AuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
