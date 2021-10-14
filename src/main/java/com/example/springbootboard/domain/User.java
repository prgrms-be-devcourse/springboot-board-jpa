package com.example.springbootboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private Hobby hobby;

    private User(String uuid, String name, int age, Hobby hobby) {
        super(name, LocalDateTime.now(), LocalDateTime.now());
        this.uuid = uuid;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User of(String uuid, String name, int age, Hobby hobby) {
        return new User(uuid, name, age, hobby);
    }
}
