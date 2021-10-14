package com.example.board.member;

import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member of(MemberDto memberDto){
        Member member = new Member(memberDto.name(), memberDto.age(), memberDto.hobby());
        return member;
    }

    public MemberDto toDto(Member member){
        MemberDto memberDto = new MemberDto(member.getId(), member.getName(), member.getAge(), member.getHobby());
        return memberDto;
    }
}
