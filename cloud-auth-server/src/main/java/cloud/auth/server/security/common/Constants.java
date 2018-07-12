package cloud.auth.server.security.common;

public interface Constants {

    String TOKEN_HEADER_PARAM = "X-Authorization";
    String XML_HTTP_REQUEST = "XMLHttpRequest";
    String X_REQUESTED_WITH = "X-Requested-With";

    String CONTENT_TYPE = "Content-type";
    String CONTENT_TYPE_JSON = "application/json";

    String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
    String MANAGE_TOKEN_BASED_AUTH_ENTRY_POINT = "/manage/**";
    String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/refresh_token";

    String SCOPES = "scopes";

}
