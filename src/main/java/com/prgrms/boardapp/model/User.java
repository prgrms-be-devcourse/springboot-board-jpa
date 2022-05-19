package com.prgrms.boardapp.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

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
    @Column(
            length = 50
    )
    private String createdBy;
    @Column(
            columnDefinition = "TIMESTAMP"
    )
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static final int NAME_MAX_LENGTH = 50;
    public static final int CREATED_BY_MAX_LENGTH = 50;
    public static final int AGE_MIN = 0;

    protected User() {
    }

    public User(Long id, String name, Integer age, String hobby, String createdBy, LocalDateTime createdAt) {
        this.validateName(name);
        this.validateAge(age);
        this.validateCreatedBy(createdBy);
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    private void validateCreatedBy(String createdBy) {
        Assert.isTrue(createdBy.length() <= CREATED_BY_MAX_LENGTH, UserErrMsg.CREATED_LENGTH_ERR_MSG.getMessage());
    }

    private void validateAge(Integer age) {
        Assert.isTrue(age >= AGE_MIN, UserErrMsg.AGE_VALIDATE_ERR.getMessage());
    }

    private void validateName(String name) {
        Assert.notNull(name, UserErrMsg.NAME_NULL_OR_EMPTY_ERR_MSG.getMessage());
        Assert.isTrue(!name.trim().isEmpty(), UserErrMsg.NAME_NULL_OR_EMPTY_ERR_MSG.getMessage());
        Assert.isTrue(name.length() <= NAME_MAX_LENGTH, UserErrMsg.NAME_LENGTH_ERR_MSG.getMessage());
    }

    public static UserBuilder builder() {
        return new UserBuilder();
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

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public static class UserBuilder {
        private Long id;
        private String name;
        private Integer age;
        private String hobby;
        private String createdBy;
        private LocalDateTime createdAt;

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

        public UserBuilder createdBy(final String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public UserBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User build() {
            return new User(this.id, this.name, this.age, this.hobby, this.createdBy, this.createdAt);
        }

        public String toString() {
            return "UserBuilder(id=" + this.id + ", name=" + this.name + ", age=" + this.age + ", hobby=" + this.hobby + ", createdBy=" + this.createdBy + ", createdAt=" + this.createdAt + ")";
        }
    }
}
