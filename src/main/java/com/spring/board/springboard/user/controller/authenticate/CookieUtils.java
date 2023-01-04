package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.exception.AuthenticateException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

public class CookieUtils {

    private static final String NO_COOKIE = "인증할 수 없는 사용자입니다.";
    private static final String USER_INFO_COOKIE_NAME = "user";
    private static final Integer COOKIE_AGE = 600;

    public static Cookie getUserCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, USER_INFO_COOKIE_NAME);

        if (Objects.isNull(cookie)) {
            throw new AuthenticateException(NO_COOKIE);
        }

        return cookie;
    }

    public static Cookie createUserInfoCookie(String cookieValue) {
        Cookie cookie = new Cookie(USER_INFO_COOKIE_NAME, cookieValue);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_AGE);
        cookie.setPath("/");
        return cookie;
    }

}
