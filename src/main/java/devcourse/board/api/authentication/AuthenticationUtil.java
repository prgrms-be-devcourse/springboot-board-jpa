package devcourse.board.api.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AuthenticationUtil {

    private AuthenticationUtil() {
    }

    public static Long getLoggedInMemberId(HttpServletRequest request) {
        assertCookieExists(request);
        return Long.valueOf(getCookieValueByCookieName(request.getCookies(), CookieConst.MEMBER_ID));
    }

    private static void assertCookieExists(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new IllegalStateException("No Cookie exists.");
        }
    }

    private static String getCookieValueByCookieName(Cookie[] cookies, String cookieName) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No Cookie exists for cookieName=" + cookieName));
    }
}
