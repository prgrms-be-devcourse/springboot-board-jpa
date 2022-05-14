package com.programmers.board.core.user.domain;

import com.programmers.board.core.commmon.entity.BaseEntity;
import com.programmers.board.core.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public void addPost(Post post){
        post.setUser(this);
    }

    @Builder
    public User(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        posts = new ArrayList<>();
    }

    public void updateAge(int age){
        this.age = age;
    }

    public void updateHobby(Hobby hobby){
        this.hobby = hobby;
    }

}
