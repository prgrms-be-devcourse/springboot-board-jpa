package com.kdt.springbootboardjpa.member.service.converter;

import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequestDto;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member requestToMember(MemberRequestDto request) {
        return Member.builder()
                .name(request.getName())
                .age(request.getAge())
                .hobby(request.getHobby())
                .build();
    }

    public MemberResponseDto memberToResponse(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .age(member.getAge())
                .hobby(member.getHobby())
                .build();
    }
}
