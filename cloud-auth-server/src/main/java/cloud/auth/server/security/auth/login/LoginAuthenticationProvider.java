package cloud.auth.server.security.auth.login;

import cloud.auth.server.security.model.UserContext;
import cloud.auth.server.service.UserInfoService;
import cloud.auth.server.service.UserRoleService;
import com.alibaba.fastjson.JSON;
import cloud.auth.server.model.UserInfo;
import cloud.auth.server.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. 对用户凭证与 数据库、LDAP或其他系统用户数据，进行验证。
 * 2. 如果用户名和密码不匹配数据库中的记录，身份验证异常将会被抛出。
 * 3. 创建用户上下文，你需要一些你需要的用户数据来填充（例如 用户名 和用户密码）
 * 4. 在成功验证委托创建JWT令牌的是在* AuthenticationSuccessHandler* 中实现。
 */
@Component
@Slf4j
public class LoginAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired private UserInfoService userService;
    @Autowired private UserRoleService roleService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        log.debug("[authentication info] - [{}]", JSON.toJSONString(authentication));
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //TODO:考虑使用缓存
        UserInfo user = userService.findByName(username);

        if(user == null) throw new UsernameNotFoundException("User not found: " + username);

//        if (!encoder.matches(password,user.getPassword())) {
//            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
//        }
        if (!StringUtils.equals(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }
        List<UserRole> roles = roleService.getRoleByUser(user);
        if (roles == null || roles.size() <= 0) throw new InsufficientAuthenticationException("User has no roles assigned");
        
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());
        
        UserContext userContext = UserContext.create(user.getUsername(), authorities);
        
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
