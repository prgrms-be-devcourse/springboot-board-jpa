package com.example.board.domain.member.controller.v2;

import com.example.board.common.session.AuthenticatedMember;
import com.example.board.common.session.SessionManager;
import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.service.MemberService;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    sessionManager.login(loginId, loginRequest.email(), response);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/mypage")
  public ResponseEntity<MemberResponse.Detail> mypage(HttpServletRequest request) {
    AuthenticatedMember authenticatedMember = sessionManager.getSession(request);

    MemberResponse.Detail detail = memberService.findById(
        authenticatedMember.getId());

    return ResponseEntity.ok(detail);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    sessionManager.logout(request, response);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
