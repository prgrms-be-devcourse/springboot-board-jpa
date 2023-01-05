package com.spring.board.springboard.user.controller;

import com.spring.board.springboard.user.controller.authenticate.Session;
import com.spring.board.springboard.user.controller.authenticate.SessionManager;
import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.domain.dto.MemberLoginDto;
import com.spring.board.springboard.user.domain.dto.MemberRequestDto;
import com.spring.board.springboard.user.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.spring.board.springboard.user.controller.authenticate.CookieUtils.createUserInfoCookie;
import static com.spring.board.springboard.user.controller.authenticate.CookieUtils.killCookie;

@RestController
@RequestMapping("/v2")
public class MemberControllerV2 {

    private final MemberService memberService;
    private final SessionManager sessionManager;

    public MemberControllerV2(MemberService memberService, SessionManager sessionManager) {
        this.memberService = memberService;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        final MemberDetailResponseDto authorizedMember = memberService.login(memberLoginDto);

        final Session session = sessionManager.getSessionOrCreateIfNotExist(authorizedMember);

        final Cookie cookie = createUserInfoCookie(session.sessionId());

        response.addCookie(cookie);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        memberService.register(memberRequestDto);

        URI loginUrl = UriComponentsBuilder.fromPath("/login")
                .build()
                .toUri();

        return ResponseEntity.created(loginUrl)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDetailResponseDto> getInfo(HttpServletRequest request) {
        final Session session = sessionManager.findSession(request);

        final MemberDetailResponseDto findMember = memberService.findByEmail(session.email());

        return ResponseEntity.ok(findMember);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        final Session session = sessionManager.findSession(request);

        sessionManager.delete(session);

        killCookie(request, response);

        return ResponseEntity
                .noContent()
                .build();
    }
}
