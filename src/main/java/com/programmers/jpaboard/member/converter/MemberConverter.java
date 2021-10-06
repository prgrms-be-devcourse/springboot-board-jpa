package com.programmers.jpaboard.member.converter;

import com.programmers.jpaboard.member.controller.dto.MemberCreationDto;
import com.programmers.jpaboard.member.controller.dto.MemberResponseDto;
import com.programmers.jpaboard.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member convertMember(MemberCreationDto memberCreationDto) {
        return Member.builder()
                .name(memberCreationDto.getName())
                .age(memberCreationDto.getAge())
                .hobbies(memberCreationDto.getHobbies())
                .build();
    }

    public MemberResponseDto convertMemberResponseDto(Member member) {
        return MemberResponseDto.builder()
                .name(member.getName())
                .age(member.getAge())
                .hobbies(member.getHobby())
                .build();
    }
}
