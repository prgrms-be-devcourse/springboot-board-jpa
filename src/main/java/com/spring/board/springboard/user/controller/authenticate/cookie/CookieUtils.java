package com.spring.board.springboard.user.controller.authenticate.cookie;

import com.spring.board.springboard.user.exception.AuthenticateException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

public class CookieUtils {

    private static final String USER_INFO_COOKIE_NAME = "user";
    private static final Integer COOKIE_AGE = 600;
    private static final Integer DEAD_COOKIE_AGE = 0;
    private static final String KILL_COOKIE_VALUE = null;

    public static Cookie getUserCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, USER_INFO_COOKIE_NAME);

        if (Objects.isNull(cookie)) {
            throw new AuthenticateException("인증할 수 없는 사용자입니다. 인증이 필요합니다.");
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

    public static void killCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, USER_INFO_COOKIE_NAME);

        if (Objects.isNull(cookie)) {
            throw new IllegalArgumentException("잘못된 요청으로 작업을 수행할 수 없습니다. 관리자에게 문의해주세요.");
        }

        cookie.setMaxAge(DEAD_COOKIE_AGE);
        cookie.setValue(KILL_COOKIE_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
