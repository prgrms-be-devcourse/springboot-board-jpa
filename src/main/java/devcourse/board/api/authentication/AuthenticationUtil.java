package devcourse.board.api.util;

import devcourse.board.api.model.CookieName;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AuthenticationUtil {

    private AuthenticationUtil() {
    }

    public static Long getLoggedInMemberId(HttpServletRequest request) {
        assertAnyMemberLoggedIn(request);
        return Long.valueOf(
                getCookieValueByCookieName(
                        request.getCookies(),
                        CookieConst.MEMBER_ID
                )
        );
    }

    private static void assertAnyMemberLoggedIn(HttpServletRequest request) {
        if (!anyMemberLoggedIn(request)) {
            throw new IllegalStateException("No member logged in.");
        }
    }

    private static boolean anyMemberLoggedIn(HttpServletRequest request) {
        return request.getCookies() != null &&
                hasCookieName(request.getCookies(), CookieName.MEMBER_ID.getCookieName());
    }

    private static boolean hasCookieName(Cookie[] cookies, String cookieName) {
        return Arrays.stream(cookies)
                .anyMatch(cookie -> cookie.getName().equals(cookieName));
    }

    private static String getCookieValueByCookieName(Cookie[] cookies, String cookieName) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Cookie Value for cookieName=" + cookieName));
    }
}
