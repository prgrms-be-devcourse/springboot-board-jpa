package com.prgrms.board.domain.user;

import com.prgrms.board.common.BaseEntity;
import com.prgrms.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Lob
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    protected User() {
    }

    @Builder
    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void changeHobby(String hobby) {
        this.hobby = hobby;
    }
}

