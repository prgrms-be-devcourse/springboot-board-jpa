package com.kdt.user.model;

import com.kdt.post.model.Post;
import lombok.*;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void update(String name, int age, String hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return MessageFormat.format("id : {0}, name : {1}, age : {2}, hobby : {3}", id, name, age, hobby);
    }

    public void addPost(Post post){
        post.setUser(this);
    }
}
