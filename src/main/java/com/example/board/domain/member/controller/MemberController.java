package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.MemberDetailResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "아이디로 회원 조회")
    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> getMemberById(@PathVariable Long id) {
        MemberDetailResponse member = memberService.findMemberById(id);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "모든 회원 조회")
    @GetMapping
    public ResponseEntity<List<MemberDetailResponse>> getAllMembers() {
        List<MemberDetailResponse> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 이름, 취미를 수정할 수 있다.")
    @PatchMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> updateMember(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest request) {
        MemberDetailResponse member = memberService.updateMember(id, request);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "회원 삭제", description = "회원 아이디로 회원을 삭제할 수 있다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMemberById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 회원 삭제", description = "전체 회원을 삭제한다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllMembers() {
        memberService.deleteAllMembers();
        return ResponseEntity.noContent().build();
    }
}
