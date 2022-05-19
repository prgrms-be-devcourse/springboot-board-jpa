package com.blessing333.boardapi.entity;

import com.blessing333.boardapi.entity.exception.UserCreateFailException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    private String hobby;

    public static User createUser(String name, int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    public static User createUserWithHobby(String name, int age, String hobby) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setHobby(hobby);
        return user;
    }

    private void setAge(int age) {
        if (age <= 0)
            throw new UserCreateFailException("age should over 0");
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
