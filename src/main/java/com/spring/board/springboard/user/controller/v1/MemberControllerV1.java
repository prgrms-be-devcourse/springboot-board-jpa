package com.spring.board.springboard.user.controller.v1;

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

import static com.spring.board.springboard.user.controller.authenticate.cookie.CookieUtils.*;

@RestController
@RequestMapping("/v1")
public class MemberControllerV1 {

    private final MemberService memberService;


    public MemberControllerV1(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        final MemberDetailResponseDto authorizedMember = memberService.login(memberLoginDto);

        Cookie cookie = createUserInfoCookie(authorizedMember.email());
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
        Cookie cookie = getUserCookie(request);
        final MemberDetailResponseDto findMember = memberService.findByEmail(cookie.getValue());

        return ResponseEntity.ok(findMember);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        killCookie(request, response);

        return ResponseEntity.noContent()
                .build();
    }

}
