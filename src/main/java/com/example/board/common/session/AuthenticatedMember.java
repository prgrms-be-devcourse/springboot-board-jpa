package com.example.board.common.session;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuthenticatedMember {
  private static final int DEFAULT_DURATION_SEC = 3600;
  private final Long id;
  private final String email;

  private LocalDateTime expiresAt;

  private AuthenticatedMember(Long id, String email) {
    this.id = id;
    this.email = email;
  }

  public static AuthenticatedMember from(Long id, String email) {
    AuthenticatedMember member = new AuthenticatedMember(id, email);
    member.expiresAt = LocalDateTime.now().plusSeconds(DEFAULT_DURATION_SEC);
    return member;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticatedMember that = (AuthenticatedMember) o;
    return Objects.equals(id, that.id) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email);
  }
}
