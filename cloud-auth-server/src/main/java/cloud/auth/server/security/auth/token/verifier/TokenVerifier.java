package cloud.auth.server.security.auth.token.verifier;

/**
 * Token验证
 */
public interface TokenVerifier {
    boolean verify(String jti);
}
