package cloud.auth.server.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link cloud.auth.server.security.model.token.Token}
 */
@Configuration
@ConfigurationProperties(prefix = "battcn.security.token")
@Getter
@Setter
public class TokenProperties {
    /**
     *  token的过期时间
     */
    private Integer expirationTime;

    /**
     * 发行人
     */
    private String issuer;

    /**
     * 使用的签名KEY
     */
    private String signingKey;

    /**
     * 刷新过期时间
     */
    private Integer refreshExpTime;

}
