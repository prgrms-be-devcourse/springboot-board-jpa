package com.prgrms.java.global.util;

import com.prgrms.java.global.exception.TokenNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.text.MessageFormat;
import java.util.Arrays;

public class CookieUtil {

    public static final String LOGIN_TOKEN = "login-token";

    public static String getValue(HttpServletRequest httpServletRequest, String cookieName) {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new TokenNotFoundException(MessageFormat.format("Please enter a {0}", cookieName)));
    }
}
