package cloud.auth.server.security.model.token;

import cloud.auth.server.security.config.TokenProperties;
import cloud.auth.server.security.exceptions.type.ExpiredTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class TokenUtil {

    public static String createToken(Claims claims, TokenProperties properties, boolean refresh){

        LocalDateTime currentTime = LocalDateTime.now();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuer(properties.getIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(properties.getExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, properties.getSigningKey());

        if(refresh){
            builder.setId(UUID.randomUUID().toString())
                   .setExpiration(Date.from(currentTime
                           .plusMinutes(properties.getRefreshExpTime())
                           .atZone(ZoneId.systemDefault()).toInstant()));
        }

        return builder.compact();
    }


    /**
     * 分析并且验证Token是否有效
     *
     * @throws BadCredentialsException 如果验证请求被拒绝，则因为凭据无效 <br> 对于要抛出的异常，它意味着该帐户既不锁定也不禁用。 <br>
     * @throws ExpiredTokenException   过期的Token
     */
    public static Jws<Claims> parseClaims(String signingKey, Token token) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.getToken());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid Token", ex);
            throw new BadCredentialsException("Invalid token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            log.info("Token is expired", expiredEx);
            throw new ExpiredTokenException(token, "Token expired", expiredEx);
        }
    }

}
