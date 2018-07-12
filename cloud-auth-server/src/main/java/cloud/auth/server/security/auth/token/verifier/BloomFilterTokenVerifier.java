package cloud.auth.server.security.auth.token.verifier;

import org.springframework.stereotype.Component;

/**
 * Token验证过滤器
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
