package com.kdt.springbootboardjpa.member.controller;

import com.kdt.springbootboardjpa.member.service.MemberService;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequest;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> save(@RequestBody MemberRequest memberRequestDto) {
        MemberResponse memberResponseDto = memberService.save(memberRequestDto);
        return ResponseEntity.ok(memberResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id, @RequestBody MemberRequest memberRequestDto) {
        MemberResponse memberResponseDto = memberService.update(id, memberRequestDto);
        return ResponseEntity.ok(memberResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(null);
    }
}
