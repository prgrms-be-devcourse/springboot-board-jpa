package com.prgrms.java.domain;

import com.prgrms.java.exception.TokenNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public record Email(@jakarta.validation.constraints.Email(regexp = EMAIL_REGEX) String value) {

    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static Email fromCookie(HttpServletRequest httpServletRequest) {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("login-token"))
                .map(Cookie::getValue)
                .map(Email::new)
                .findFirst()
                .orElseThrow(() -> new TokenNotFoundException("Please enter a login token"));
    }
}
