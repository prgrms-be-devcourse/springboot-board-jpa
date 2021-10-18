package com.programmers.springbootboard.member.dto.request;

public class MemberSignRequest {
    private String email;
    private String name;
    private String age;
    private String hobby;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}
