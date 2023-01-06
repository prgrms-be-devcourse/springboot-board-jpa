package com.prgrms.springbootboardjpa.member.dto;

import com.prgrms.springbootboardjpa.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {
    private long memberId;
    private String email;
    private String password;
    private String name;
    private int age;
    private String hobby;

    public MemberResponse() {
    }

    public MemberResponse(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
        this.age = member.getAge();
        this.hobby = member.getHobby();
    }
}
