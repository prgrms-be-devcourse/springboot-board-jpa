package com.example.springbootboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    private static final String nameRegex = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$";

    @Builder
    public User(String name, Integer age, Hobby hobby, String createdBy, LocalDateTime createdAt) {
        super(createdBy, createdAt);

        validate(name, age);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void change(String name, Integer age, Hobby hobby) {
        validate(name, age);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }


    public void validate(String name, Integer age) {
        validName(name);
        validAge(age);
    }

    public void validName(String name) {
        Assert.notNull(name, "User name should not be null");

        if (!Pattern.matches(nameRegex, name)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid User name. name = {0}", name));
        }
    }

    public void validAge(Integer age) {

        if (age == null)
            return;

        if (age <= 0) {
            throw new IllegalArgumentException(MessageFormat.format("User age should be over 0. age = {0}", age));
        }
    }
}
