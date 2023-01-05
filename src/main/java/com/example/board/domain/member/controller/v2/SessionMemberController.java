package com.example.board.domain.member.controller.v2;

import com.example.board.common.exception.SessionNotFoundException;
import com.example.board.common.exception.UnAuthorizedException;
import com.example.board.common.util.CookieUtils;
import com.example.board.common.util.CookieUtils.CookieBuilder;
import com.example.board.domain.member.controller.v2.session.AuthenticatedMember;
import com.example.board.domain.member.controller.v2.session.SessionManager;
import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.service.MemberService;
import java.net.URI;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/members/v2")
public class SessionMemberController {

  private final MemberService memberService;

  private final SessionManager sessionManager;

  @Value("${session.cookie.name}")
  private String cookieName;

  @Value("${session.duration}")
  private int duration;

  public SessionMemberController(MemberService memberService, SessionManager sessionManager) {
    this.memberService = memberService;
    this.sessionManager = sessionManager;
  }

  @PostMapping
  public ResponseEntity<Void> signUp(@RequestBody MemberRequest.SignUp signUpRequest) {
    memberService.save(signUpRequest);

    URI uri = UriComponentsBuilder.newInstance()
        .path("/members/v2/login")
        .build()
        .encode()
        .toUri();

    return ResponseEntity.created(uri)
        .build();
  }

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody MemberRequest.Login loginRequest, HttpServletResponse response) {
    Long loginId = memberService.login(loginRequest);
    UUID sessionId = sessionManager.login(loginId, loginRequest.email());
    Cookie cookie = new CookieUtils.CookieBuilder(cookieName, String.valueOf(sessionId))
        .httpOnly(Boolean.TRUE)
        .maxAge(duration)
        .path("/")
        .build();

    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/mypage")
  public ResponseEntity<MemberResponse.Detail> mypage(HttpServletRequest request) {
    Cookie sessionIdCookie = CookieUtils.getCookie(request, cookieName);

    try{
      UUID sessionId = UUID.fromString(sessionIdCookie.getValue());
      AuthenticatedMember authenticatedMember = sessionManager.getSession(sessionId);

      MemberResponse.Detail detail = memberService.findById(
          authenticatedMember.getId());

      return ResponseEntity.ok(detail);
    } catch(IllegalArgumentException | SessionNotFoundException e){
      throw new UnAuthorizedException(
          String.format("UnAuthorized cookie value: \"%s\"", sessionIdCookie.getValue()));
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    Cookie sessionIdCookie = CookieUtils.getCookie(request, cookieName);

    try{
      UUID sessionId = UUID.fromString(sessionIdCookie.getValue());
      sessionManager.removeSession(sessionId);

      Cookie cookie = new CookieBuilder(cookieName, null)
          .maxAge(0)
          .path("/")
          .build();
      response.addCookie(cookie);

      return new ResponseEntity<>(HttpStatus.OK);
    } catch(IllegalArgumentException | SessionNotFoundException e){
      throw new UnAuthorizedException(
          String.format("UnAuthorized cookie value: \"%s\"", sessionIdCookie.getValue()));
    }
  }
}
