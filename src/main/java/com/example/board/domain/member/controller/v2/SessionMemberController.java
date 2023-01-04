package com.example.board.domain.member.controller.v2;

import com.example.board.common.exception.UnAuthorizedException;
import com.example.board.common.util.AuthorityUtils;
import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.service.MemberService;
import java.net.URI;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v2/members")
public class SessionMemberController {

  private final MemberService memberService;

  @Value("${cookie.name}")
  private String cookieName;

  @Value("${cookie.max-age}")
  private int maxAge;

  public SessionMemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping
  public ResponseEntity<Void> signUp(@RequestBody MemberRequest.SignUp signUpRequest) {
    Long savedId = memberService.save(signUpRequest);

    URI uri = UriComponentsBuilder.newInstance()
        .path("/api/v2/member/login")
        .build()
        .expand(savedId)
        .encode()
        .toUri();

    return ResponseEntity.created(uri)
        .build();
  }

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody MemberRequest.Login loginRequest) {
    Long loginId = memberService.login(loginRequest);
    ResponseCookie responseCookie = ResponseCookie.from(cookieName, String.valueOf(loginId))
        .httpOnly(Boolean.TRUE)
        .maxAge(maxAge)
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .build();
  }

  @GetMapping("/mypage")
  public ResponseEntity<MemberResponse.Detail> mypage(HttpServletRequest request) {
    Cookie loginIdCookie = AuthorityUtils.getCookie(request, cookieName);

    try{
      Long memberId = Long.valueOf(loginIdCookie.getValue());

      MemberResponse.Detail detail = memberService.findById(memberId);

      return ResponseEntity.ok(detail);
    } catch(NumberFormatException e){
      throw new UnAuthorizedException(
          String.format("UnAuthorized cookie value: \"%s\"", loginIdCookie.getValue()));
    }

  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
