package cloud.auth.server.security.common;

import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * WEB工具类。
 */
public class WebUtil {

    public static boolean isAjax(HttpServletRequest request) {
        return Constants.XML_HTTP_REQUEST.equals(request.getHeader(Constants.X_REQUESTED_WITH));
    }

    public static boolean isAjax(SavedRequest request) {
        return request.getHeaderValues(Constants.X_REQUESTED_WITH).contains(Constants.XML_HTTP_REQUEST);
    }

    public static boolean isContentTypeJson(SavedRequest request) {
        return request.getHeaderValues(Constants.CONTENT_TYPE).contains(Constants.CONTENT_TYPE_JSON);
    }
}
