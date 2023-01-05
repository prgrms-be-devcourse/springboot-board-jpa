package com.example.board.common.util;

import com.example.board.common.exception.UnAuthorizedException;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

public class CookieUtils {

  private CookieUtils(){}

  public static Cookie getCookie(HttpServletRequest request, String cookieName){
    Assert.notNull(cookieName, "parameter [cookieName] should not be null");
    Assert.notNull(request.getCookies(), "[request.getCookies()] should not be null");
    return Arrays.stream(request.getCookies())
        .filter(cookie -> cookieName.equals(cookie.getName()))
        .findAny()
        .orElseThrow(
            () -> new UnAuthorizedException(String.format("Cookie name %s not found", cookieName)));
  }

  public static class CookieBuilder{
    private final Cookie cookie;

    public CookieBuilder(String cookieName, String value){
      this.cookie = new Cookie(cookieName, value);
    }

    public CookieBuilder domain(String domain){
      cookie.setDomain(domain);
      return this;
    }

    public CookieBuilder path(String path){
      cookie.setPath(path);
      return this;
    }

    public CookieBuilder maxAge(int expiresAt){
      cookie.setMaxAge(expiresAt);
      return this;
    }

    public CookieBuilder httpOnly(boolean httpOnly){
      cookie.setHttpOnly(httpOnly);
      return this;
    }

    public Cookie build(){
      return cookie;
    }
  }
}
