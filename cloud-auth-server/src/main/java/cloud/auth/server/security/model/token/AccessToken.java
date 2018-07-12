package cloud.auth.server.security.model.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证Token
 */
@Getter
@AllArgsConstructor
public final class AccessToken implements Token {

    private final String token;
    private Claims claims;

    public AccessToken(String token){
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey) {
        return TokenUtil.parseClaims(signingKey, this);
    }


}
