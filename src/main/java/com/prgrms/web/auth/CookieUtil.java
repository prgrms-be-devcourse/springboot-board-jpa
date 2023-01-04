package com.prgrms.web.auth;

import com.prgrms.exception.ErrorCode;
import com.prgrms.exception.customException.AuthenticationFailedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    private static final String COOKIE_NAME = "userId";

    private CookieUtil() {}

    public static Cookie createCookie(Long userId) {

        return new Cookie(COOKIE_NAME, String.valueOf(userId));
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = getCookie(request);
        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request) {

        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);

        if (cookie == null) {
            throw new AuthenticationFailedException(ErrorCode.AUTHENCTICATION_FAILED);
        }
        return cookie;
    }

}
