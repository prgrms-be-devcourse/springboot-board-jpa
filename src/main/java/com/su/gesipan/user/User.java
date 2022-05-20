package com.su.gesipan.user;

import com.su.gesipan.common.audit.BaseTimeEntity;
import com.su.gesipan.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @PositiveOrZero
    private Long age;
    @Column(length = 50)
    private String hobby;
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    private User(String name, Long age, String hobby) {
        checkArgument(age > 0, "나이는 양수여야 합니다.");
        checkArgument(hobby.length() < 50, "취미는 50자 이내여야 합니다.");

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User of(String name, Long age, String hobby){
        return new User(name, age, hobby);
    }

    public void setHobby(String hobby){
        checkArgument(hobby.length() < 50, "취미는 50자 이내로 작성해주세요.");
        this.hobby = hobby;
    }

    // 연관관계 편의 메서드 - post 와 관계를 맺어준다.
    public Post addPost(Post post){
        if(!posts.contains(post)){
            posts.add(post);
            post.relationshipWith(this);
        }
        return post;
    }
    public void addPosts(Post ...posts){
        for(var post : posts){
            addPost(post);
        }
    }
    public List<Post> getPosts(){
        return new ArrayList<>(posts);
    }
    public void deletePost(Post target){
        posts.removeIf(post -> Objects.equals(post.getId(), target.getId()));
    }
}
