package com.kdt.bulletinboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "user")
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "hobby", length = 50)
    private Hobby hobby;

    @OneToMany(mappedBy = "id")
    private List<Post> posts = new ArrayList<>();

    public User(String userName, Hobby hobby, String createBy) {
        this.userName = userName;
        this.hobby = hobby;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(createBy);
    }

    //연관관계 편의 메서드
    public void addPost(Post post) {
        post.setUser(this);
    }

}
