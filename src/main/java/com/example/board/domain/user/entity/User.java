package com.example.board.domain.user.entity;

import com.example.board.domain.userhobby.entity.UserHobby;
import com.example.board.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

import static com.example.board.global.validator.UserValidator.validateAge;
import static com.example.board.global.validator.UserValidator.validateName;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "{exception.entity.user.name.null}")
    @Length(min = 1, message = "{exception.entity.user.name.length}")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "{exception.entity.user.age.null}")
    @PositiveOrZero(message = "{exception.entity.user.age.length}")
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserHobby> userHobbies = new ArrayList<>();

    @Builder
    public User(String name, int age) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
    }

    public void addHobby(UserHobby userHobby) {
        userHobbies.add(userHobby);
        userHobby.targetUser(this);
    }
}
