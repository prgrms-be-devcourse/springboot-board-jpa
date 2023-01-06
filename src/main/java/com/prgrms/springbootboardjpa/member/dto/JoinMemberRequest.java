package com.prgrms.springbootboardjpa.member.dto;

import com.prgrms.springbootboardjpa.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinMemberRequest {

    private String email;
    private String password;
    private String name;
    private int age;
    private String hobby;

    public JoinMemberRequest() {
    }

    public JoinMemberRequest(String email, String password, String name, int age, String hobby) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Member toMember() {
        return new Member(this.email, this.password, this.name, this.age, this.hobby);
    }
}
