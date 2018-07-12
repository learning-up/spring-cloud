package cloud.auth.server.security.auth.token;

import cloud.auth.server.security.auth.AuthenticationToken;
import cloud.auth.server.security.config.TokenProperties;
import cloud.auth.server.security.model.UserContext;
import cloud.auth.server.security.model.token.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 提供实现身份验证的实例
 */
@Component
@Slf4j
public class TokenAuthenticationProvider implements AuthenticationProvider {

	@Autowired  private TokenProperties tokenProperties;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AccessToken rawAccessToken = (AccessToken) authentication.getCredentials();
		long startTime = System.currentTimeMillis();
		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(tokenProperties.getSigningKey());
		log.debug("[验证Token消耗时间] - [{}]", (System.currentTimeMillis() - startTime));
		String subject = jwsClaims.getBody().getSubject();
		@SuppressWarnings("unchecked")
		List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
		List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(toList());
		UserContext context = UserContext.create(subject, authorities);
		return new AuthenticationToken(context, context.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (AuthenticationToken.class.isAssignableFrom(authentication));
	}
}
