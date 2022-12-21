package com.spring.board.springboard.user.domain;

public class MemberResponseDTO {
    private String name;
    private Integer age;
    private Hobby hobby;

    public MemberResponseDTO(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
        this.hobby = member.getHobby();
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }
}
