package cloud.auth.server.security.exceptions;

import cloud.auth.server.security.model.token.RawAccessToken;
import cloud.auth.server.security.model.token.Token;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.AuthenticationException;


/**
 * 过期的Token
 */
public class ExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private Token token;

    public ExpiredTokenException(RawAccessToken rawAccessToken, String msg, ExpiredJwtException expiredEx) {
        super(msg);
    }

    public ExpiredTokenException(Token token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
