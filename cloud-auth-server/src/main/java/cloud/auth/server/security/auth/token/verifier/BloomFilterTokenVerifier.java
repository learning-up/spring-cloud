package cloud.auth.server.security.auth.token.verifier;

import org.springframework.stereotype.Component;

/**
 * 检查撤销令牌
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {

    @Override
    public boolean verify(String jti) {
        return true;
    }
}
