package cloud.auth.server.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    public final String desc;

}
