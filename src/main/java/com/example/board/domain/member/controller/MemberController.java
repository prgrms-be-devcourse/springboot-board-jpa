package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.*;
import com.example.board.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody LoginRequest request) {
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

    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> getMemberById(@PathVariable Long id) {
        MemberDetailResponse member = memberService.findMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<MemberDetailResponse>> getAllMembers() {
        List<MemberDetailResponse> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> updateMember(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest request) {
        MemberDetailResponse member = memberService.updateMember(id, request);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMemberById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllMembers() {
        memberService.deleteAllMembers();
        return ResponseEntity.noContent().build();
    }
}
