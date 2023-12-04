package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.LoginRequest;
import com.example.board.domain.member.dto.LoginResponse;
import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberDetailResponse;
import com.example.board.domain.member.service.MemberService;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberService.loginMember(request));
    }

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
