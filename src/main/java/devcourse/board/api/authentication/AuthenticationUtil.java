package devcourse.board.api.authentication;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {

    private AuthenticationUtil() {
    }

    public static Long getLoggedInMemberId(HttpServletRequest request) {
        Cookie memberIdCookie = WebUtils.getCookie(request, CookieConst.MEMBER_ID);

        if (memberIdCookie == null) {
            throw new IllegalStateException("No member logged in.");
        }

        return Long.valueOf(memberIdCookie.getValue());
    }
}
