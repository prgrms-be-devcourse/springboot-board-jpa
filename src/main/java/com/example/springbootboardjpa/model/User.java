package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Getter
@ToString(exclude = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @NotNull
    private Integer age;

    @Column(length = 50)
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    public User(String name, Integer age, String hobby) {
        checkArgument(isNotBlank(name), "이름은 Null 이거나 공백일 수 없습니다.");
        checkNotNull(age,"나이 Null 일 수 없습니다.");
        checkArgument(age > 0, "나이는 0보다 커야합니다.");
        checkArgument(hobby == null || hobby.length() <= 50, "취미의 유효 글자 수 50을 초과하였습니다.");

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User(String name, Integer age) {
        this(name, age, null);
    }

    public void changeName(String name) {
        checkArgument(isNotBlank(name));
        this.name = name;
    }

}
