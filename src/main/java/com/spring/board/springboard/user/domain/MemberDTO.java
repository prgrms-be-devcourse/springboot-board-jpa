package com.spring.board.springboard.user.domain;

public class MemberDTO {
    private String name;
    private Integer age;
    private Hobby hobby;

    public MemberDTO() {
    }

    public MemberDTO(String name, Integer age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static MemberDTO toDTO(Member member){
        return new MemberDTO(
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }

    public static Member toEntity(MemberDTO memberDTO){
        return new Member(
                memberDTO.getName(),
                memberDTO.getAge(),
                memberDTO.getHobby()
        );
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
