package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.*;
import com.example.board.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력하면 토큰 발급")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberService.loginMember(request));
    }

    @Operation(summary = "회원 가입", description = "회원 정보를 입력하여 회원 가입. 중복된 이메일은 예외를 발생한다")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberDetailResponse> signUp(@Valid @RequestBody MemberCreateRequest request) {
        MemberDetailResponse member = memberService.createMember(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.id())
                .toUri();
        return ResponseEntity.created(location)
                .body(member);
    }

    // ToDo "/api/v1/reissue" 구현
}
