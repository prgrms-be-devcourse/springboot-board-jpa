package dev.jpaboard.user.domain;

import dev.jpaboard.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(length = 5, nullable = false)
    private String name;

    @Column(length = 3, nullable = false)
    private int age;

    @Column(length = 20)
    private String hobby;

    @Builder
    private User(String email, String password, String name, int age, String hobby) {
        this.email = new Email(email);
        this.password = new Password(password);
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
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getEmail() {
        return email.getEmail();
    }

}
