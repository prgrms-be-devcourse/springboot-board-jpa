package prgms.boardmission.member.converter;

import prgms.boardmission.domain.Member;
import prgms.boardmission.member.dto.MemberDto;

import java.time.LocalDateTime;

public final class MemberConverter {
    public static Member convertToMember(MemberDto memberDto){
        Member member = new Member();
        member.setUserId(memberDto.userId());
        member.setName(memberDto.name());
        member.setAge(memberDto.age());
        member.setHobby(memberDto.hobby());
        member.setCratedAt(LocalDateTime.now());

        return member;
    }

    public static MemberDto convertToMemberDto(Member member){
        Long userId = member.getUserId();
        String name = member.getName();
        int age = member.getAge();
        String hobby = member.getHobby();

        return new MemberDto(userId,name,age,hobby);
    }
}
