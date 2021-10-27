package com.assignment.bulletinboard.user;

import com.assignment.bulletinboard.BaseEntity;
import com.assignment.bulletinboard.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Size(min = 14, max = 100)
    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = true, length = 50)
    private String hobby;

    public void changeUserName(String name){
        this.name = name;
        LocalDateTime now = LocalDateTime.now();
        setUpdatedAt(now);
    }

    public void changeUserAge(int age){
        this.age = age;
        LocalDateTime now = LocalDateTime.now();
        setUpdatedAt(now);
    }

    public void changeUserHobby(String hobby) {
        this.hobby = hobby;
        LocalDateTime now = LocalDateTime.now();
        setUpdatedAt(now);
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Post> PostList = new ArrayList<>();

    public void addPost(Post post){
        if(Objects.nonNull(post)){
            this.PostList.remove(post);
        }
        this.PostList.add(post);
    }
}
