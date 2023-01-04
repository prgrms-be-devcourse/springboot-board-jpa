package com.example.board.domain.member.controller.v2.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.board.common.exception.SessionNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionManagerTest {

  private final SessionManager sessionManager;

  private final SessionStorage sessionStorage;

  SessionManagerTest(){
    this.sessionStorage = new SessionStorage();
    this.sessionManager = new SessionManager(this.sessionStorage);
  }

  private UUID saveSingleAuthenticatedMember(){
    return sessionManager.login(1L, "email@naver.com");
  }

  @AfterEach
  public void tearDown(){
    sessionStorage.clear();
  }

  @Test
  @DisplayName("로그인하여 세션 정보를 저장할 수 있습니다.")
  void saveSession(){
    //given & when
    UUID sessionId = sessionManager.login(1L, "email@naver.com");

    //then
    AuthenticatedMember member = sessionStorage.get(sessionId);
    assertThat(member.getId()).isEqualTo(1L);
    assertThat(member.getEmail()).isEqualTo("email@naver.com");
  }

  @Test
  @DisplayName("이미 로그인한 사용자가 다시 로그인하면 새로운 세션 정보가 저장됩니다.")
  void reissue(){
    //given
    UUID oldSessionId = sessionManager.login(1L, "email@naver.com");
    AuthenticatedMember oldLoginedMember = sessionManager.getSession(oldSessionId);
    LocalDateTime oldExpiresAt = oldLoginedMember.getExpiresAt();

    //when
    UUID newSessionId = sessionManager.login(1L, "email@naver.com");
    AuthenticatedMember newLoginedMember = sessionManager.getSession(newSessionId);
    LocalDateTime newExpiresAt = newLoginedMember.getExpiresAt();

    //then
    assertThat(newSessionId).isNotEqualTo(oldSessionId);
    assertThat(newExpiresAt).isNotEqualTo(oldExpiresAt);
  }

  @Test
  @DisplayName("세션을 통해 사용자 인증 정보를 조회할 수 있습니다.")
  void getSession(){
    //given & when
    UUID sessionId = sessionManager.login(1L, "email@naver.com");

    //when
    AuthenticatedMember member = sessionManager.getSession(sessionId);
    assertThat(member.getId()).isEqualTo(1L);
    assertThat(member.getEmail()).isEqualTo("email@naver.com");
  }

  @Test
  @DisplayName("로그아웃 시 세션 정보가 삭제됩니다.")
  void removeSession(){
    //given
    UUID sessionId = saveSingleAuthenticatedMember();

    //when
    sessionManager.removeSession(sessionId);

    //then
    assertThrows(SessionNotFoundException.class, () -> sessionManager.getSession(sessionId));
  }

  @Test
  @DisplayName("유효하지 않은 session id로 권한 확인을 요청하면 권한 확인에 실패한다.")
  void checkAuthentication(){
    //given & when
    saveSingleAuthenticatedMember();

    //then
    assertThrows(SessionNotFoundException.class, () -> sessionManager.checkSession(UUID.randomUUID()));
  }
}