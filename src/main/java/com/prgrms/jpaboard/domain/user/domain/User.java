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

    @Column(nullable = false)
    private String name;

    private int age;

    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, Long id, String name, int age, String hobby) {
        super(createdBy, createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String name;
        private int age;
        private String hobby;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder age(int age) {
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

        public UserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            return new User(createdBy, createdAt, updatedAt, id, name, age, hobby);
        }
    }

    public void updateAge(int age) {
        this.age = age;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateHobby(String hobby) {
        this.hobby = hobby;
        this.updatedAt = LocalDateTime.now();
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
