package org.programmers.springbootboardjpa.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @Id
    @GeneratedValue
    private Long userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    private String hobby;
}