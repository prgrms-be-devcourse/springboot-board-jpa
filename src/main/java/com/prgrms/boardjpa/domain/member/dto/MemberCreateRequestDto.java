package com.prgrms.boardjpa.domain.member.dto;

import com.prgrms.boardjpa.domain.member.Hobby;
import com.prgrms.boardjpa.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberCreateRequestDto {
    private final String name;
    private final int age;
    private final Hobby hobby;

    public Member toMember() {
        return Member.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
