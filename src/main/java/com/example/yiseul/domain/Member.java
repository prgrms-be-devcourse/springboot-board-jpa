package com.example.yiseul.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

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

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String name, int age, String hobby) {
        this.name = name;
        this.age = Age.from(age);
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        posts.add(post);
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

    private int validateUpdateInteger(Integer update, Integer origin) {
        if (update == null) {

            return origin;
        }
        return update;
    }


    private void validationPositiveAge(int age) {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("나이는 0세 이상이어야 합니다.");
        }
    }
}
