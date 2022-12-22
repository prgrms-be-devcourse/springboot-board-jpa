package com.prgrms.domain.user;

import com.prgrms.domain.BaseEntity;
import com.prgrms.domain.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 2, max = 10, message = "이름은 2글자 이상, 10글자 이하 여야 합니다")
    @Column(length = 10, nullable = false)
    private String name;

    private String hobby;

    @Min(value = 1, message = "나이는 1살 이상이어야 합니다")
    private Integer age;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();


    public User() {
    }

    public User(String name, String hobby, Integer age) {
        this(null, name, hobby, age);
    }

    public User(Long id, String name, String hobby, Integer age) {
        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
        this.setCreatedBy(name);
        this.setCreatedAt(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Integer getAge() {
        return age;
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
