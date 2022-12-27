package com.prgrms.java.domain;

import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.text.MessageFormat;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private HobbyType hobby;

    protected User() {
    }

    public User(Long id, String name, int age, HobbyType hobby) {
        validName(name);
        validAge(age);
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User(String name, int age, HobbyType hobby) {
        this(null, name, age, hobby);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public HobbyType getHobby() {
        return hobby;
    }

    private static void validAge(int age) {
        Assert.state(age > 1, MessageFormat.format("age must be more than one. [age]: {0}", age));
    }

    private static void validName(String name) {
        Assert.hasLength(name, MessageFormat.format("name must not be empty or null. [name]: {0}", name));
        Assert.state(name.length() <= 30, MessageFormat.format("name must be less than or equal to 30 characters. [name]: {0}", name));
    }

}
