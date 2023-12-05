package com.example.board.domain.member.entity;

import com.example.board.domain.common.entity.BaseEntity;
import com.example.board.domain.role.entity.Role;
import com.example.board.global.exception.CustomException;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.board.global.exception.ErrorCode.INVALID_PASSWORD;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
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

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "last_updated_password")
    private LocalDateTime lastUpdatedPassword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MemberRole> roles = new ArrayList<>();

    public Member(String email, String password, String name, int age, String hobby, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.isDeleted = false;
        this.lastUpdatedPassword = LocalDateTime.now();
        addRoles(roles);
    }

    private void addRoles(List<Role> roles) {
        this.roles = roles.stream()
                .map(role -> new MemberRole(this, role))
                .collect(Collectors.toList());
    }

    public void updateNameAndHobby(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        this.lastUpdatedPassword = LocalDateTime.now();
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password))
            throw new CustomException(INVALID_PASSWORD);
    }
}
