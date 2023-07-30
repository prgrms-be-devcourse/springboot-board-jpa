package com.example.yiseul.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    public static final int MIN_AGE = 0;

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(length = 20)
    private String hobby;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>(); // 밖에서 add 할 수 있지 않나

    public Member(String name, int age, String hobby) {
        validationPositiveAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void updateInfo(String updateName, int updateAge, String updateHobby) {
        this.name = validateUpdateString(updateName, this.name);

        int updatedAge = validateUpdateInteger(updateAge, this.age); // 질문 : origin값이면 결국 검증이 두번되고있다. (생성때 업데이트때) 개선방법??
        validationPositiveAge(updatedAge);
        this.age = updatedAge;

        this.hobby = validateUpdateString(updateHobby, this.hobby);
    }

    private String validateUpdateString(String update, String origin) {
        if (update == null) {

            return origin;
        }
        return update;
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
