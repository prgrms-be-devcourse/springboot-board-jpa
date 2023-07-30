package com.example.yiseul.converter;

import com.example.yiseul.domain.Member;
import com.example.yiseul.dto.member.MemberCreateRequestDto;
import com.example.yiseul.dto.member.MemberResponseDto;

public class MemberConverter {

    public static Member convertMember(MemberCreateRequestDto createRequestDto) {

        return new Member(createRequestDto.name(), createRequestDto.age(), createRequestDto.hobby());
    }

    public static MemberResponseDto convertMemberDto(Member member) {

        return new MemberResponseDto(member.getId(), member.getName(), member.getAge(), member.getHobby(), member.getCreatedAt(), member.getCreatedBy());
    }
}
