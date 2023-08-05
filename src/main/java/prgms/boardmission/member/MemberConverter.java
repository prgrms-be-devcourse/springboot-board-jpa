package prgms.boardmission.member;

import prgms.boardmission.domain.Member;
import prgms.boardmission.member.dto.MemberDto;

public final class MemberConverter {
    public static Member convertToMember(MemberDto memberDto) {
        long id = memberDto.userId();
        String name = memberDto.name();
        int age = memberDto.age();
        String hobby = memberDto.hobby();

        return new Member(id,name,age,hobby);
    }

    public static MemberDto convertToMemberDto(Member member) {
        Long userId = member.getId();
        String name = member.getName();
        int age = member.getAge();
        String hobby = member.getHobby();

        return new MemberDto(userId, name, age, hobby);
    }
}
