package com.prgrms.jpaboard.domain.user.domain;

import com.prgrms.jpaboard.domain.post.domain.Post;
import com.prgrms.jpaboard.global.common.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    private Integer age;

    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String createdBy, String name, Integer age, String hobby) {
        super(createdBy);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String name;
        private Integer age;
        private String hobby;
        private String createdBy;

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public UserBuilder hobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public UserBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public User build() {
            return new User(createdBy, name, age, hobby);
        }
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAge(Integer age) {
        this.age = age;
    }

    public void updateHobby(String hobby) {
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
