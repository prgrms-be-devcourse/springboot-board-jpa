package com.kdt.Board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer age;
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;


    @Builder
    public User(String name, Integer age, String hobby, List<Post> postList) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postList = postList;
        if (this.postList == null) this.postList = new ArrayList<>();
    }
}
