package com.assignment.bulletinboard.user;

import com.assignment.bulletinboard.BaseEntity;
import com.assignment.bulletinboard.post.Post;
import lombok.*;

import javax.persistence.*;
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
    @Column(name = "post_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    private String hobby;

    public void changeUserName(String name){
        this.name = name;
    }

    public void changeUserAge(int age){
        this.age = age;
    }

    public void changeUserHobby(String hobby) {
        this.hobby = hobby;
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
