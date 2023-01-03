package com.prgrms.java.util;

import com.prgrms.java.domain.Email;
import com.prgrms.java.exception.TokenNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CookieUtil {

    public static final String LOGIN_TOKEN = "login-token";

    public static Email getLoginToken(HttpServletRequest httpServletRequest) {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals(LOGIN_TOKEN))
                .map(Cookie::getValue)
                .map(Email::new)
                .findFirst()
                .orElseThrow(() -> new TokenNotFoundException("Please enter a login token"));
    }
}
