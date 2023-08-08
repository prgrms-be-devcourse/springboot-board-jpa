package com.blackdog.springbootBoardJpa.domain.user.model;

import com.blackdog.springbootBoardJpa.domain.user.model.vo.Age;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Name;
import com.blackdog.springbootBoardJpa.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Column
    private String hobby;

    protected User() {
    }

    public static User builder() {
        return new User();
    }

    public User name(final Name name) {
        this.name = name;
        return this;
    }

    public User age(final Age age) {
        this.age = age;
        return this;
    }

    public User hobby(final String hobby) {
        this.hobby = hobby;
        return this;
    }

    public User build() {
        return new User(
                this.name,
                this.age,
                this.hobby
        );
    }

    public User(
            Name name,
            Age age,
            String hobby
    ) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

}
