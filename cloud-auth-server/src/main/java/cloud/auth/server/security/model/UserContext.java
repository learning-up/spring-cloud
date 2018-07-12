package cloud.auth.server.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor
public class UserContext {

    @Getter  private final String username;
    @Getter  private final List<GrantedAuthority> authorities;

    public static UserContext create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, authorities);
    }

}
