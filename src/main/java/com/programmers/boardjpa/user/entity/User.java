package com.programmers.boardjpa.user.entity;

import com.programmers.boardjpa.global.common.BaseEntity;
import com.programmers.boardjpa.user.exception.UserErrorCode;
import com.programmers.boardjpa.user.exception.UserException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    private String hobby;

    @Builder
    public User(String name, int age, String hobby) {
        validateAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateAge(int age) {
        if (age < 0 || age > 110) {
            throw new UserException(UserErrorCode.INVALID_AGE_RANGE);
        }
    }
}
