package com.example.yiseul.controller;

import com.example.yiseul.dto.member.*;
import com.example.yiseul.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponseDto signUp(@RequestBody @Valid MemberCreateRequestDto createRequestDto){

        return memberService.createMember(createRequestDto);
    }

    @GetMapping
    public MemberPageResponseDto getMembers(Pageable pageable){

        return memberService.getMembers(pageable);
    }

    @GetMapping("/{memberId}")
    public MemberResponseDto getMember(@PathVariable Long memberId){

        return memberService.getMember(memberId);
    }

    @PatchMapping("/{memberId}")
    public void updateMember(
            @PathVariable Long memberId,
            @RequestBody @Valid MemberUpdateRequestDto updateRequestDto
    ) {

        memberService.updateMember(memberId, updateRequestDto);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);
    }

    @GetMapping("/cursor") // 커서 방식
    public MemberCursorResponseDto getMembersByCursor(
            @RequestParam(required = false) Long cursorId,
            @RequestParam int size
            ){

        return memberService.findMemberByCursor(cursorId, size);
    }
}
