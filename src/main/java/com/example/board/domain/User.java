package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name", nullable = false, length = 20)
    private String name;

    private int age;
    private Hobby hobby;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Post> posts;

    public void addPost(Post post){
        posts.add(post);
    }
}
