package cloud.auth.server.security.model.token;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证Token
 */
@Getter
@AllArgsConstructor
public final class AccessToken implements Token {

    private final String rawToken;
    private Claims claims;

    @Override
    public String getToken() {
        return this.rawToken;
    }

}
