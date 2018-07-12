package cloud.auth.server.service;

import cloud.auth.server.model.UserInfo;
import cloud.auth.server.model.UserRole;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserRoleService {

    public List<UserRole> getRoleByUser(UserInfo user) {
        if ("test".equals(user.getUsername())) {
            //@see ExpressionUrlAuthorizationConfigurer  private static String hasAnyRole(String... authorities) 带 ROLE_
            return Lists.newArrayList(new UserRole("ROLE_ADMIN"));
        }
        return null;
    }
}
