package com.example.yiseul.controller;

import com.example.yiseul.domain.Member;
import com.example.yiseul.dto.member.MemberCreateRequestDto;
import com.example.yiseul.dto.member.MemberResponseDto;
import com.example.yiseul.dto.member.MemberUpdateRequestDto;
import com.example.yiseul.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    private static final int DEFAULT_SIZE = 10;

    @PostMapping
    public MemberResponseDto signUp(@RequestBody MemberCreateRequestDto createRequestDto){
        log.info(createRequestDto.name());

        return memberService.createMember(createRequestDto);
    }

    @GetMapping
    public Page<MemberResponseDto> getMembers(Pageable pageable){

        return memberService.getMembers(pageable);
    }

    @GetMapping("/{memberId}")
    public MemberResponseDto getMember(@PathVariable Long memberId){

        return memberService.getMember(memberId);
    }

    @PatchMapping("/{memberId}")
    public void updateMember(@PathVariable Long memberId,
                             @RequestBody MemberUpdateRequestDto updateRequestDto) {

        memberService.updateMember(memberId, updateRequestDto);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);
    }
}
