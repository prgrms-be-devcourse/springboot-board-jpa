package com.example.board.domain.member.controller.v2.session;

import java.time.LocalDateTime;

public class AuthenticatedMember {
  private final Long id;
  private final String email;

  private LocalDateTime expiresAt;

  private AuthenticatedMember(Long id, String email) {
    this.id = id;
    this.email = email;
  }

  public static AuthenticatedMember fromDuration(Long id, String email, int durationSec){
    AuthenticatedMember member = new AuthenticatedMember(id, email);
    member.expiresAt = LocalDateTime.now().plusSeconds(durationSec);
    return member;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public boolean isExpired(){
    return expiresAt.isAfter(LocalDateTime.now());
  }
}
