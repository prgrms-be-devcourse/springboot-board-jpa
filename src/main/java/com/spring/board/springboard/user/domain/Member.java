package com.spring.board.springboard.user.domain;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.validation.EmailValidator;
import com.spring.board.springboard.user.exception.AuthenticateException;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
@SequenceGenerator(
        name = "MEMBER_SEQUENCE",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1)
public class Member {

    private static final Integer MIN_AGE = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQUENCE")
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "hobby")
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "member")
    private final List<Post> postList = new ArrayList<>();

    protected Member() {
    }

    public Member(String email, String password, String name, Integer age, Hobby hobby) {
        validate(email, password, name, age);
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public List<Post> getPostList() {
        return new ArrayList<>(postList);
    }

    public void login(String requestPassword) {
        if (!Objects.equals(requestPassword, this.password)) {
            throw new AuthenticateException("password가 올바르지 않습니다.");
        }
    }

    private void validate(String email, String password, String name, Integer age) {
        Assert.notNull(password, "비밀번호는 필수입니다.");
        Assert.notNull(age, "나이는 필수입니다.");

        EmailValidator.validate(email);

        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }

        if (age <= MIN_AGE) {
            throw new IllegalArgumentException("10살 미만은 서비스 이용이 불가능합니다");
        }

        if (password.length() > 30
                || password.length() < 5) {
            throw new IllegalArgumentException("비밀번호는 5자리 이상 30자리 이하여야 합니다.");
        }
    }
}
