package com.spring.board.springboard.user.controller;

import com.spring.board.springboard.user.controller.authenticate.SessionStorage;
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
import static com.spring.board.springboard.user.controller.authenticate.SessionStorage.putSession;
import static com.spring.board.springboard.user.controller.authenticate.SessionUtils.validateMember;

@RestController
@RequestMapping("/v2")
public class MemberControllerV2 {

    private final MemberService memberService;

    public MemberControllerV2(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        final MemberDetailResponseDto authorizedMember = memberService.login(memberLoginDto);

        String sessionId = SessionStorage.getSessionOrCreateIfNotExist(
                authorizedMember.memberId());

        Cookie cookie = createUserInfoCookie(sessionId);

        putSession(sessionId, authorizedMember.memberId());

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

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberDetailResponseDto> getInfo(@PathVariable Integer memberId, HttpServletRequest request) {
        final MemberDetailResponseDto findMember = memberService.findById(memberId);

        validateMember(request, findMember);

        return ResponseEntity.ok(findMember);
    }
}
