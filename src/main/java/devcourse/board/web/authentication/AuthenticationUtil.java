package devcourse.board.web.authentication;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {

    public static final String COOKIE_NAME = "memberId";

    private AuthenticationUtil() {
    }

    public static Long getLoggedInMemberId(HttpServletRequest request) {
        Cookie memberIdCookie = WebUtils.getCookie(request, COOKIE_NAME);

        if (memberIdCookie == null) {
            throw new IllegalStateException("No member logged in.");
        }

        return Long.valueOf(memberIdCookie.getValue());
    }
}
