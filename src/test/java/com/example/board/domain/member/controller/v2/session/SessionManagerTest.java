package com.example.board.domain.member.controller.v2.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.board.common.exception.SessionNotFoundException;
import com.example.board.common.session.AuthenticatedMember;
import com.example.board.common.session.SessionManager;
import com.example.board.common.session.SessionStorage;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionManagerTest {

  private final SessionManager sessionManager;

  private final SessionStorage sessionStorage;

  SessionManagerTest(){
    this.sessionStorage = new SessionStorage();
    this.sessionManager = new SessionManager(this.sessionStorage, "sessionId", 3600);
  }

  private UUID saveSingleAuthenticatedMember(){
    Cookie cookie = sessionManager.login(1L, "email@naver.com");
    return UUID.fromString(cookie.getValue());
  }

  @AfterEach
  public void tearDown(){
    sessionStorage.clear();
  }

  @Test
  @DisplayName("로그인하여 세션 정보를 저장할 수 있습니다.")
  void saveSession(){
    //given & when
    Cookie cookie = sessionManager.login(1L, "email@naver.com");
    UUID sessionId = UUID.fromString(cookie.getValue());

    //then
    AuthenticatedMember member = sessionStorage.get(sessionId);
    assertThat(member.getId()).isEqualTo(1L);
    assertThat(member.getEmail()).isEqualTo("email@naver.com");
  }

  @Test
  @DisplayName("이미 로그인한 사용자가 다시 로그인하면 새로운 세션 정보가 저장됩니다.")
  void reissue(){
    //given
    Cookie oldCookie = sessionManager.login(1L, "email@naver.com");
    UUID oldSessionId = UUID.fromString(oldCookie.getValue());

    //when
    Cookie newCookie = sessionManager.login(1L, "email@naver.com");
    UUID newSessionId = UUID.fromString(newCookie.getValue());

    //then
    assertThat(newSessionId).isNotEqualTo(oldSessionId);
  }

  @Test
  @DisplayName("세션을 통해 사용자 인증 정보를 조회할 수 있습니다.")
  void getSession(){
    //given & when
    UUID sessionId = saveSingleAuthenticatedMember();

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
    sessionManager.logout(sessionId);

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