package com.kdt.jpaboard.domain.board.user;

import com.kdt.jpaboard.domain.board.BaseEntity;
import com.kdt.jpaboard.domain.board.post.Posts;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Posts> posts = new ArrayList<>();

    public void addPosts(Posts post) {
        this.posts.add(post);
    }

    public void changeUserInfo(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

}
