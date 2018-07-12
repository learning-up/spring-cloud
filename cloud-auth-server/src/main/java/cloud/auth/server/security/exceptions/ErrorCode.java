package cloud.auth.server.security.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 详细错误类型（枚举）.
 */
public enum ErrorCode {

    GLOBAL(2),
    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11);
    
    private int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
