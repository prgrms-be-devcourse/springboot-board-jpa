package com.programmers.springboard.controller;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.jwt.JwtAuthentication;
import com.programmers.springboard.jwt.JwtAuthenticationToken;
import com.programmers.springboard.request.LoginRequest;
import com.programmers.springboard.response.MemberResponse;
import com.programmers.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/user/login")
    public MemberResponse login(@RequestBody LoginRequest request){

        JwtAuthenticationToken authToken = new JwtAuthenticationToken(request.principal(), request.credentials());
        Authentication resultToken = authenticationManager.authenticate(authToken);
        JwtAuthenticationToken authenticated = (JwtAuthenticationToken) resultToken;
        JwtAuthentication principal = (JwtAuthentication) authenticated.getPrincipal();

        Member member = (Member) authenticated.getDetails();
        return new MemberResponse(principal.getToken(), principal.getUsername(), member.getGroups().getName());
    }

    @GetMapping("/user/me")
    public MemberResponse me(@AuthenticationPrincipal JwtAuthentication jwtAuthentication){
        Member member = memberService.getMemberByLoginId(jwtAuthentication.getUsername());
        return new MemberResponse(jwtAuthentication.getToken(), jwtAuthentication.getUsername(), member.getGroups().getName());
    }
}
