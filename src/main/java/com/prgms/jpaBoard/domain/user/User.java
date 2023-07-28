package com.prgms.jpaBoard.domain.user;

import com.prgms.jpaBoard.global.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "hobby", nullable = false)
    private HobbyType hobby;

    protected User() {

    }

    public User(String name, int age, HobbyType hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        setCreatedBy(name);
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
