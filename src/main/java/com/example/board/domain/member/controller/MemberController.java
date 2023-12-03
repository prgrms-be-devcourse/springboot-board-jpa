package com.example.board.domain.member.controller;

import com.example.board.domain.email.service.MailService;
import com.example.board.domain.member.dto.*;
import com.example.board.domain.member.service.MemberService;
import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.example.board.global.security.util.SecurityUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse member = memberService.createMember(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.id())
                .toUri();
        return ResponseEntity.created(location)
                .body(member);
    }

    @PostMapping("/email/sign-up")
    public ResponseEntity<Void> sendSignUpMail(@RequestParam(name = "email") String email) {
        mailService.sendSignUpMail(email);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/email/password")
    public ResponseEntity<Void> sendResetPasswordMail(@RequestParam(name = "email") String email) {
        mailService.sendResetPasswordMail(email);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest){
        memberService.resetPassword(passwordResetRequest);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse login = memberService.login(loginRequest);
        return ResponseEntity.ok(login);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long id) {
        MemberResponse member = memberService.findMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyProfile() {
        Long currentMemberId = getCurrentUserIdCheck();
        MemberResponse member = memberService.findMemberById(currentMemberId);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest request) {
        MemberResponse member = memberService.updateMember(id, request);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMemberById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteMemberByIds(@RequestParam List<Long> ids) {
        memberService.deleteMemberByIds(ids);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllMembers() {
        memberService.deleteAllMembers();
        return ResponseEntity.noContent().build();
    }
}
