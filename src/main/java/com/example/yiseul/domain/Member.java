package com.example.yiseul.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    public static final int MAX_NAME_LENGTH = 20;

    public static final int MAX_HOBBY_LENGTH = 20;

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = MAX_NAME_LENGTH, nullable = false)
    private String name;

    @Embedded
    private Age age;

    @Column(length = MAX_HOBBY_LENGTH)
    private String hobby;

    public Member(String name, Integer age, String hobby) {
        validationName(name);
        validationHobby(hobby);
        this.name = name;
        this.age = new Age(age);
        this.hobby = hobby;
    }

    public void updateInfo(String name, Integer age, String hobby) {
        validationName(name);
        validationHobby(hobby);
        this.name = name;
        this.age = new Age(age);
        this.hobby = hobby;
    }

    public int getAge() {
        return age.getAge();
    }

    private void validationName(String name) {
        if (name.length() < MAX_NAME_LENGTH || name == null) {

            throw new IllegalArgumentException("이름은 최대 20글까지 입력되어야만 합니다.");
        }
    }

    private void validationHobby(String hobby) {
        if (hobby.length() < MAX_HOBBY_LENGTH || hobby == null) {

            throw new IllegalArgumentException("취미는 최대 20글까지 입력되어야만 합니다.");
        }
    }
}
