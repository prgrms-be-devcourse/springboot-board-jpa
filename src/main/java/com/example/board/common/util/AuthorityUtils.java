package com.example.board.common.util;

import com.example.board.common.exception.UnAuthorizedException;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

public class AuthorityUtils {

  private AuthorityUtils(){}

  public static Cookie getCookie(HttpServletRequest request, String cookieName){
    Assert.notNull(cookieName, "parameter [cookieName] should not be null");
    return Arrays.stream(request.getCookies())
        .filter(cookie -> cookieName.equals(cookie.getName()))
        .findAny()
        .orElseThrow(
            () -> new UnAuthorizedException(String.format("Cookie name %s not found", cookieName)));
  }
}
