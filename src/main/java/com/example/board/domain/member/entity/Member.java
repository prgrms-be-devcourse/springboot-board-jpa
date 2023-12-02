package com.example.board.domain.member.entity;

import com.example.board.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
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

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false)
    private String hobby;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String email, String name,  int age, String hobby) {
        this.email = email;
        this.name = name;
        this.password = "test1234!";
        this.age = age;
        this.hobby = hobby;
        this.role = Role.ROLE_USER;
    }

    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public void updateNameAndHobby(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }

    public boolean isSameEmail(String email) {
        return Objects.equals(email, this.email);
    }
}
