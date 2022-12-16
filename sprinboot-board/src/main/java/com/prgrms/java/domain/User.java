package com.prgrms.java.domain;

import jakarta.persistence.*;

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

    public User() {
    }

    public User(Long id, String name, int age, HobbyType hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
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
}
