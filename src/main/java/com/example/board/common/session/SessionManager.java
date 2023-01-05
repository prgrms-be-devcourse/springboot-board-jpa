package com.example.board.common.session;

import com.example.board.common.exception.ForbiddenException;
import com.example.board.common.exception.SessionNotFoundException;
import com.example.board.common.util.CookieUtils;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

  private final SessionStorage sessionStorage;

  private final String cookieName;

  private final int duration;

  public SessionManager(SessionStorage sessionStorage,
      @Value("${session.cookie.name}") String cookieName, @Value("${session.duration}") int duration){
    this.sessionStorage = sessionStorage;
    this.cookieName = cookieName;
    this.duration = duration;
  }

  public void login(Long memberId, String email, HttpServletResponse response){
    Cookie loginCookie = doLogin(memberId, email);
    response.addCookie(loginCookie);
  }

  private Cookie doLogin(Long memberId, String email){
    Optional<UUID> optionalSessionId = sessionStorage.getLoginedSessionId(memberId, email);

    UUID sessionId;
    if(optionalSessionId.isPresent()){
      sessionId = reissue(optionalSessionId.get(), memberId, email);
    } else{
      sessionId = createSession(memberId, email);
    }

    return new CookieUtils.CookieBuilder(cookieName, String.valueOf(sessionId))
        .httpOnly(Boolean.TRUE)
        .maxAge(duration)
        .path("/")
        .build();
  }

  private UUID createSession(Long memberId, String email) {
    return sessionStorage.save(memberId, email, duration);
  }

  private UUID reissue(UUID sessionId, Long memberId, String email) {
    sessionStorage.remove(sessionId);
    return sessionStorage.save(memberId, email, duration);
  }

  public AuthenticatedMember getSession(UUID sessionId) {
    checkSession(sessionId);
    return sessionStorage.get(sessionId);
  }

  public AuthenticatedMember getSession(HttpServletRequest request) {
    Cookie cookie = getCookie(request);
    checkSession(cookie);
    UUID sessionId = convertToSessionId(cookie);
    return sessionStorage.get(sessionId);
  }

  public void logout(UUID sessionId) {
    checkSession(sessionId);
    sessionStorage.remove(sessionId);
  }

  public void logout(HttpServletRequest request, HttpServletResponse response){
    Cookie cookie = getCookie(request);
    checkSession(cookie);
    UUID sessionId = convertToSessionId(cookie);
    sessionStorage.remove(sessionId);
    Cookie removeCookie = new CookieUtils.CookieBuilder(cookieName, null)
        .maxAge(0)
        .path("/")
        .build();
    response.addCookie(removeCookie);
  }

  private Cookie getCookie(HttpServletRequest request) {
    try{
      return CookieUtils.getCookie(request, cookieName);
    } catch(IllegalArgumentException e){
      throw new ForbiddenException("Invalid Authority. ");
    }
  }

  private UUID convertToSessionId(Cookie cookie){
    try{
      return UUID.fromString(cookie.getValue());
    } catch (IllegalArgumentException e){
      throw new SessionNotFoundException(
          String.format("Invalid session id. Session id %s is not format of UUID.", cookie.getValue()));
    }
  }

  private void checkSession(Cookie cookie){
    UUID sessionId = convertToSessionId(cookie);
    checkSession(sessionId);
  }

  private void checkSession(UUID sessionId){
    if(!sessionStorage.containsKey(sessionId)){
      throw new SessionNotFoundException(
          String.format("Invalid session id. Session id %s Not found", sessionId.toString()));
    }
  }
}
