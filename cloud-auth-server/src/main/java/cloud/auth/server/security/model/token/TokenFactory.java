package cloud.auth.server.security.model.token;

import cloud.auth.server.security.common.Constants;
import cloud.auth.server.security.config.TokenProperties;
import cloud.auth.server.security.model.Scopes;
import cloud.auth.server.security.model.UserContext;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Token创建工厂类 {@link Token}.
 */
@Component
public class TokenFactory {

    @Autowired private TokenProperties properties;


    /**
     * 利用JJWT 生成 Token
     *
     * @param context
     * @return
     */
    public AccessToken createAccessToken(UserContext context) {
        Optional.ofNullable(context.getUsername()).orElseThrow(() -> new IllegalArgumentException("Cannot create Token without username"));
        Optional.ofNullable(context.getAuthorities()).orElseThrow(() -> new IllegalArgumentException("User doesn't have any privileges"));
        Claims claims = Jwts.claims().setSubject(context.getUsername());
        claims.put(Constants.SCOPES, context.getAuthorities().stream().map(Object::toString).collect(toList()));
        String token = TokenUtil.createToken(claims, properties, false);
        return new AccessToken(token, claims);
    }

    /**
     * 生成 刷新 RefreshToken
     *
     * @param userContext
     * @return
     */
    public Token createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create Token without username");
        }

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
//        claims.put(Constants.SCOPES, userContext.getAuthorities().stream().map(Object::toString).collect(toList()));
        claims.put(Constants.SCOPES, Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
        String token = TokenUtil.createToken(claims, properties, true);

        return new AccessToken(token, claims);
    }

    public JSONObject tokenWrapper(UserContext userContext){
        AccessToken accessToken = this.createAccessToken(userContext);
        Token refreshToken = this.createRefreshToken(userContext);

        JSONObject tokenMap = new JSONObject();
        tokenMap.put("claims", accessToken.getClaims());
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());
        return tokenMap;
    }
}
