package devcourse.board.web.authentication;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {

    public static final String COOKIE_NAME = "memberId";
    public static final String SESSION_NAME = "sessionId";

    private AuthenticationUtil() {
    }

    public static Long getLoggedInMemberId(HttpServletRequest request) {
        Cookie memberIdCookie = WebUtils.getCookie(request, COOKIE_NAME);

        if (memberIdCookie == null) {
            throw new IllegalStateException("No member logged in.");
        }

        return Long.valueOf(memberIdCookie.getValue());
    }

    public static Cookie newEmptyCookie(String cookieName) {
        Cookie emptyCookie = new Cookie(cookieName, null);
        emptyCookie.setPath("/");
        emptyCookie.setMaxAge(0);
        return emptyCookie;
    }
}
