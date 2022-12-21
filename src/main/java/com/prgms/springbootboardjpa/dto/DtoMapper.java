package com.prgms.springbootboardjpa.dto;

import com.prgms.springbootboardjpa.model.Member;

public class DtoMapper {
    public static MemberDto memberToMemberDto(Member member) {
        return new MemberDto(member.getMemberId(), member.getName(), member.getAge(), member.getHobby());
    }

    public static Member memberDtoToMember(MemberDto memberDto) {
        return new Member(memberDto.getMemberId(), memberDto.getName(), memberDto.getAge(), memberDto.getHobby());
    }
}
