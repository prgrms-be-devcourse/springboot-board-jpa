package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.exception.AuthenticateException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

public class CookieUtils {

    private static final String NO_COOKIE = "사용자 정보를 알 수 없어 접근이 제한됩니다.";
    private static final String USER_INFO_COOKIE_NAME = "user";
    private static final Integer COOKIE_AGE = 600;

    public static void validateMemberByCookie(HttpServletRequest request, MemberDetailResponseDto findMember) {
        Cookie cookie = getUserCookie(request);

        if (isNotEqual(cookie.getValue(), findMember.email())) {
            throw new AuthenticateException("권한이 없습니다.");
        }
    }

    public static Cookie getUserCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, USER_INFO_COOKIE_NAME);

        if (Objects.isNull(cookie)) {
            throw new AuthenticateException(NO_COOKIE);
        }

        return cookie;
    }

    public static Cookie createUserInfoCookie(String cookieValue) {
        System.out.println(cookieValue);
        Cookie cookie = new Cookie(USER_INFO_COOKIE_NAME, cookieValue);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_AGE);
        return cookie;
    }

    private static boolean isNotEqual(String origin, String input) {
        return !Objects.equals(origin, input);
    }

}
