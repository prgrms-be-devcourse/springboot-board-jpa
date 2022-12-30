package com.prgrms.domain.user;

import static com.prgrms.domain.Regex.EMAIL_REGEX;
import static com.prgrms.domain.Regex.PASSWORD_REGEX;

import com.prgrms.domain.BaseEntity;
import com.prgrms.domain.Regex;
import com.prgrms.domain.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String hobby;

    private Integer age;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String name, String hobby, Integer age, String email, String password) {

        this(null, name, hobby, age, email, password);
    }

    public User(Long id, String name, String hobby, Integer age, String email, String password) {

        Assert.isTrue(verifyRegex(email, EMAIL_REGEX), "이메일 형식을 확인해주세요");
        Assert.isTrue(verifyRegex(password, PASSWORD_REGEX),
            "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.");
        validateAge(age);
        validateName(name);

        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
        this.email = email;
        this.password = password;
        this.beWrittenBy(name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    public Integer getAge() {
        return age;
    }

    private boolean verifyRegex(String subject, Regex regex) {
        String ePattern = regex.getRgx();
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(subject);
        return m.matches();
    }

    private void validateAge(int age) {
        Assert.isTrue(age > 0, "나이는 1 이상 이여야 합니다.");
    }

    private void validateName(String name) {
        Assert.hasText(name, "이름은 1글자 이상이여야 합니다");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getName(),
            user.getName()) && Objects.equals(getHobby(), user.getHobby())
            && Objects.equals(getPosts(), user.getPosts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getHobby(), getPosts());
    }
}
