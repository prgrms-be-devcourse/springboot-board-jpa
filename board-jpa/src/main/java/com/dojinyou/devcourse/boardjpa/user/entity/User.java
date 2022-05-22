package com.dojinyou.devcourse.boardjpa.user.entity;

import com.dojinyou.devcourse.boardjpa.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 50)
    private String name;

    @NotNull
    @Positive
    private int age;

    @NotBlank
    @Length(max = 50)
    private String hobby;

    protected User() {
    }

    private User(Builder builder) {
        valid(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.hobby = builder.hobby;
    }

    private void valid(Builder builder) {
        if (builder.id != null && builder.id <= 0) {
            throw new IllegalArgumentException();
        }

        if (builder.name == null || builder.name.length() > 50 || builder.name.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (builder.age <= 0) {
            throw new IllegalArgumentException();
        }

        if (builder.hobby == null || builder.hobby.length() > 50 || builder.hobby.isBlank()) {
            throw new IllegalArgumentException();
        }
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

    public String getHobby() {
        return hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.id == null) return false;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private int age;
        private String hobby;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
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
