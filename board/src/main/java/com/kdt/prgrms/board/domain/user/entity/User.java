package com.kdt.prgrms.board.domain.user.entity;

import com.kdt.prgrms.board.domain.user.Hobby;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private int age;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    protected User() {

    }

    private User(UserBuilder builder) {

        this.name = builder.name;
        this.age = builder.age;
        this.hobby = builder.hobby;
    }

    public static class UserBuilder {

        private String name;
        private int age;
        private Hobby hobby;

        public UserBuilder name(String value) {

            this.name = value;
            return this;
        }

        public UserBuilder age(int value) {

            this.age = value;
            return this;
        }

        public UserBuilder hobby(Hobby value) {

            this.hobby = value;
            return this;
        }

        public User build() {

            return new User(this);
        }
    }

    public static UserBuilder builders() {

        return new UserBuilder();
    }
}
