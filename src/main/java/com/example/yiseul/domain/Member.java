package com.example.yiseul.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    @Embedded
    private Age age;

    @Column(length = 20)
    private String hobby;

    public Member(String name, int age, String hobby) {
        this.name = name;
        this.age = Age.from(age);
        this.hobby = hobby;
    }

    public void updateInfo(String updateName, Integer updateAge, String updateHobby) {
        changeName(updateName);
        this.age.changeAge(updateAge);
        changeHobby(updateHobby);
    }

    private void changeName(String updateName) {
        if (updateName != null) {
            this.name =  updateName;
        }
    }

    private void changeHobby(String updateHobby) {
        if (updateHobby != null) {
            this.hobby = updateHobby;
        }
    }

    public int getAge() {
        return age.getAge();
    }
}
