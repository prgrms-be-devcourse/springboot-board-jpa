package com.prgrms.dev.springbootboardjpa.domain.user;

import com.prgrms.dev.springbootboardjpa.domain.BaseEntity;
import com.prgrms.dev.springbootboardjpa.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "hobby", nullable = false)
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, Hobby hobby, int age) {
        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }

}
