package cloud.auth.server.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfig
 */
public class PasswordEncoderConfig implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
