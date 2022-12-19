package com.prgrms.boardjpa.domain.member.dto;

import com.prgrms.boardjpa.domain.member.Hobby;
import com.prgrms.boardjpa.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDto {
    private final Long id;
    private final String name;
    private final int age;
    private final Hobby hobby;

    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(member.getId(), member.getName(), member.getAge(), member.getHobby());
    }
}
