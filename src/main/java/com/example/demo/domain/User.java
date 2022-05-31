package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BasedTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Size(min = 1, max = 30)
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @PositiveOrZero
    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.addCreator(name);
    }
}
