package com.example.board.domain.member.controller.v2.session;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

  @Value("${session.cookie.name}")
  private String sessionId;

  @Value("${session.duration}")
  private int duration;

  private final SessionStorage sessionStorage;

  public SessionManager(SessionStorage sessionStorage){
    this.sessionStorage = sessionStorage;
  }

  public UUID save(Long memberId, String email){
    return sessionStorage.save(memberId, email, duration);
  }

  public AuthenticatedMember getLoginMember(UUID sessionId) {
    return sessionStorage.get(sessionId);
  }
}
