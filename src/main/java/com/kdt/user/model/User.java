package com.kdt.user.model;

import com.kdt.common.model.BaseEntity;
import com.kdt.post.model.Post;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {
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

        super.update();
    }

    public void addPost(Post post){
        post.setUser(this);
    }
}
