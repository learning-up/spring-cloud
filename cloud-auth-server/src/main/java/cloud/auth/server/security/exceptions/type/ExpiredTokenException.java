package cloud.auth.server.security.exceptions.type;

import cloud.auth.server.security.model.token.Token;
import org.springframework.security.core.AuthenticationException;


/**
 * 过期的Token
 */
public class ExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private Token token;

    public ExpiredTokenException(Token token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
