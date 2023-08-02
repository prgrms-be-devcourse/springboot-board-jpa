package dev.jpaboard.user.domain;

import dev.jpaboard.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private static final String EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 25, nullable = false)
    @Email(regexp = EMAIL_REGEX)
    private String email;

    @Column(length = 20, nullable = false)
    @Pattern(regexp = PASSWORD_REGEX)
    private String password;

    @Column(length = 5, nullable = false)
    private String name;

    @Column(length = 3, nullable = false)
    private int age;

    @Column(length = 20)
    private String hobby;

    @Builder
    private User(@Valid String email, @Valid String password, String name, int age, String hobby) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void update(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
