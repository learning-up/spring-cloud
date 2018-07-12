package cloud.auth.server.controller;

import cloud.auth.server.model.UserInfo;
import cloud.auth.server.model.UserRole;
import cloud.auth.server.security.auth.token.extractor.TokenExtractor;
import cloud.auth.server.security.auth.token.verifier.TokenVerifier;
import cloud.auth.server.security.common.Constants;
import cloud.auth.server.security.config.TokenProperties;
import cloud.auth.server.security.exceptions.type.InvalidTokenException;
import cloud.auth.server.security.model.UserContext;
import cloud.auth.server.security.model.token.AccessToken;
import cloud.auth.server.security.model.token.RefreshToken;
import cloud.auth.server.security.model.token.Token;
import cloud.auth.server.security.model.token.TokenFactory;
import cloud.auth.server.service.UserInfoService;
import cloud.auth.server.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    @Autowired private TokenProperties tokenProperties;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired private TokenFactory tokenFactory;
    @Autowired private TokenExtractor tokenExtractor;
    @Autowired private UserInfoService userInfoService;
    @Autowired private UserRoleService userRoleService;

    @GetMapping("/api/auth/refresh_token")
    public AccessToken refreshToken(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(Constants.TOKEN_HEADER_PARAM));
        AccessToken rawToken = new AccessToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenProperties.getSigningKey())
                .orElseThrow(() -> new InvalidTokenException("Token验证失败"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidTokenException("Token验证失败");
        }

        String subject = refreshToken.getSubject();
        UserInfo user = Optional.ofNullable(userInfoService.findByName(subject)).orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + subject));
        List<UserRole> roles = Optional.ofNullable(userRoleService.getRoleByUser(user)).orElseThrow(() -> new InsufficientAuthenticationException("用户没有分配角色"));
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);
        return tokenFactory.createAccessToken(userContext);
    }

}
