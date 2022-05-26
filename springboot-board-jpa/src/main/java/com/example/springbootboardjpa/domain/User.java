package com.example.springbootboardjpa.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // https://erjuer.tistory.com/106
public class User extends DateEntity {
    @Id
    @Column(updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 40, updatable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = true)
    private String hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private final List<Post> posts = new ArrayList<>();

    @Builder(toBuilder = true)
    public User(UUID id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    // https://velog.io/@park2348190/JPA-Entity%EC%9D%98-equals%EC%99%80-hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return age == user.age && id.equals(user.id) && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
