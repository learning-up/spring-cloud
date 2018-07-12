package cloud.auth.server.security.model.token;

import cloud.auth.server.security.common.Constants;
import cloud.auth.server.security.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Optional;

/**
 * Token刷新工具类
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken implements Token {

    @Getter private Jws<Claims> claims;

    /**
     * Creates and validates Refresh token
     *
     * @param token
     * @param signingKey
     * @return
     * @throws BadCredentialsException
     */
    public static Optional<RefreshToken> create(AccessToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);
        @SuppressWarnings("unchecked")
        List<String> scopes = claims.getBody().get(Constants.SCOPES, List.class);
        if (scopes == null || scopes.isEmpty()
                || scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))
                ) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public String getJti() {
        return this.claims.getBody().getId();
    }

    public String getSubject() {
        return this.claims.getBody().getSubject();
    }
}
