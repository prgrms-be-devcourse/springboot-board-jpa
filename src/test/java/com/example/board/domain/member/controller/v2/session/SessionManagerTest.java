package com.example.board.domain.member.controller.v2.session;

import static org.assertj.core.api.Assertions.*;

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

  @AfterEach
  private void tearDown(){
    sessionStorage.clear();
  }

  @Test
  @DisplayName("로그인하여 세션 정보를 저장할 수 있습니다.")
  void saveSession(){
    //given & when
    UUID sessionId = sessionManager.save(1L, "email@naver.com");

    //then
    AuthenticatedMember member = sessionManager.getLoginMember(sessionId);
    assertThat(member.getId()).isEqualTo(1L);
    assertThat(member.getEmail()).isEqualTo("email@naver.com");
  }
}