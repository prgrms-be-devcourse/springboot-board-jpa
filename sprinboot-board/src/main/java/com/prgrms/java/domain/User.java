package com.prgrms.java.domain;

import com.prgrms.java.global.exception.LoginFailedException;
import jakarta.persistence.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private HobbyType hobby;

    protected User() {
    }

    public User(Long id, String name, String email, String password, int age, HobbyType hobby) {
        validate(name, email, password, age);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.hobby = hobby;
    }

    public User(String name, String email, String password, int age, HobbyType hobby) {
        this(null, name, email, password, age, hobby);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public HobbyType getHobby() {
        return hobby;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void login(String password) {
        if (!Objects.equals(password, this.password)) {
            throw new LoginFailedException(MessageFormat.format("Failed to login. Please Check password. [password]: {0}", password));
        }
    }

    private void validate(String name, String email, String password, int age) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);
        validateAge(age);
    }

    private void validateAge(int age) {
        Assert.state(age > 0, "age must be over 0.");
    }

    private void validatePassword(String password) {
        Assert.state(StringUtils.hasText(password), "password must be at least one character");
        Assert.state(password.length() <= 30, "password must be under 31.");
    }

    private void validateEmail(String email) {
        Assert.state(StringUtils.hasText(email), "email must be at least one character");
        Assert.state(email.length() <= 30, "email must be under 31.");
        Assert.state(Pattern.matches(Email.EMAIL_REGEX, email), "Not supported email format.");
    }

    private void validateName(String name) {
        Assert.state(StringUtils.hasText(name), "name must be at least one character");
        Assert.state(name.length() <= 30, "name must be under 31.");
    }
}
