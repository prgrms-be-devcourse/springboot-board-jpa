package com.prgrms.boardapp.model;

import com.prgrms.boardapp.constant.UserErrMsg;
import org.springframework.util.Assert;

import javax.persistence.*;

import static com.prgrms.boardapp.util.CommonValidate.validateNotNullString;

@Entity
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false,
            length = 50
    )
    private String name;
    @Column(
            nullable = false
    )
    private Integer age;
    private String hobby;
    @Embedded
    private CommonEmbeddable commonEmbeddable;
    @Embedded
    private Posts posts;

    public static final int NAME_MAX_LENGTH = 50;
    public static final int AGE_MIN = 0;

    protected User() {
    }

    public User(Long id, String name, Integer age, String hobby, CommonEmbeddable commonEmbeddable, Posts posts) {
        this.validateName(name);
        this.validateAge(age);
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.commonEmbeddable = (commonEmbeddable == null) ? CommonEmbeddable.builder().build() : commonEmbeddable;
        this.posts = (posts == null) ? new Posts() : posts;
    }

    private void validateAge(Integer age) {
        Assert.isTrue(age >= AGE_MIN, UserErrMsg.AGE_VALIDATE_ERR.getMessage());
    }

    private void validateName(String name) {
        validateNotNullString(name);
        Assert.isTrue(name.length() <= NAME_MAX_LENGTH, UserErrMsg.NAME_LENGTH_ERR_MSG.getMessage());
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getHobby() {
        return this.hobby;
    }

    public Posts getPosts() {
        return posts;
    }

    public CommonEmbeddable getCommonEmbeddable() {
        return this.commonEmbeddable;
    }

    public void createPost(Post post) {
        post.changeUser(this);
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String name;
        private Integer age;
        private String hobby;
        private CommonEmbeddable commonEmbeddable;
        private Posts posts;

        UserBuilder() {
        }

        public UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UserBuilder age(final Integer age) {
            this.age = age;
            return this;
        }

        public UserBuilder hobby(final String hobby) {
            this.hobby = hobby;
            return this;
        }

        public UserBuilder commonEmbeddable(final CommonEmbeddable commonEmbeddable) {
            this.commonEmbeddable = commonEmbeddable;
            return this;
        }

        public UserBuilder posts(final Posts posts) {
            this.posts = posts;
            return this;
        }

        public User build() {
            return new User(this.id, this.name, this.age, this.hobby, this.commonEmbeddable, this.posts);
        }
    }
}
