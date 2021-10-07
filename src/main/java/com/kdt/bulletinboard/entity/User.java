package com.kdt.bulletinboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "user")
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID userId;

    @Column(name = "name", length = 50, nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "hobby", length = 50)
    private Hobby hobby;

    @OneToMany(mappedBy = "post")
    private List<Post> posts;

    //연관관계 편의 메서드
    public void addPost(Post post) {
        post.setUser(this);
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, Hobby hobby) {
        this.userName = userName;
        this.hobby = hobby;
    }

}
