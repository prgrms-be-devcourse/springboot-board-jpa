package com.spring.board.springboard.user.controller;

import com.spring.board.springboard.user.domain.dto.MemberLoginDto;
import com.spring.board.springboard.user.domain.dto.MemberRequestDto;
import com.spring.board.springboard.user.domain.dto.MemberResponseDto;
import com.spring.board.springboard.user.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class MemberController {

    private final MemberService memberService;


    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        final MemberResponseDto findMember = memberService.login(memberLoginDto, response);

        URI rootUrl = UriComponentsBuilder.fromPath("/")
                .build()
                .toUri();

        return ResponseEntity.ok()
                .location(rootUrl)
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
    public ResponseEntity<MemberResponseDto> getInfo(@PathVariable Integer memberId, HttpServletRequest request) {
        final MemberResponseDto findMember = memberService.findById(memberId, request);

        return ResponseEntity.ok(findMember);
    }

}
