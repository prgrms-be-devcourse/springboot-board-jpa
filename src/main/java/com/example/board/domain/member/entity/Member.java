package com.example.board.domain.member.entity;

import com.example.board.domain.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false)
    private String hobby;

    public Member(String email, String name, int age, String hobby) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void updateNameAndHobby(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }

    public boolean isSameEmail(String email) {
        return Objects.equals(email, this.email);
    }
}
