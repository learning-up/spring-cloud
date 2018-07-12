package cloud.auth.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRole {

    private String roleName;

    public UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String authority() {
        return this.getRoleName();
    }
}
