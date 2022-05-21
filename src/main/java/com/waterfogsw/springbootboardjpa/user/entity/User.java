package com.waterfogsw.springbootboardjpa.user.entity;

import com.waterfogsw.springbootboardjpa.common.entity.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(updatable = false)
    private String name;

    @Email
    @NotBlank
    @Column(updatable = false, unique = true)
    private String email;

    @Min(0)
    private Integer age;
    private String hobby;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    protected User() {
    }

    private User(Builder builder) {
        Assert.notNull(builder.name, "Name should not be null");
        Assert.notNull(builder.email, "Email should not be null");

        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.hobby = builder.hobby;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private Integer age;
        private String hobby;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder hobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
