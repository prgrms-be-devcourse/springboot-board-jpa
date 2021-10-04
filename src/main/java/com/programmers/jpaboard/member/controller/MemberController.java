package com.programmers.jpaboard.member.controller;

import com.programmers.jpaboard.member.ApiResponse;
import com.programmers.jpaboard.member.controller.converter.MemberConverter;
import com.programmers.jpaboard.member.controller.dto.MemberCreationDto;
import com.programmers.jpaboard.member.controller.dto.MemberResponseDto;
import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberConverter memberConverter;

    @PostMapping("/members")
    public ApiResponse<MemberResponseDto> createMember(@ModelAttribute MemberCreationDto memberCreationDto) {
        Member member = memberConverter.convertMember(memberCreationDto);
        Member saved = memberService.saveMember(member);
        MemberResponseDto memberResponseDto = memberConverter.convertMemberResponseDto(saved);

        return ApiResponse.ok("Creation Success", memberResponseDto);
    }
}
