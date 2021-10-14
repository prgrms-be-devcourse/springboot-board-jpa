package com.example.jpaboard.user.domain;

import com.example.jpaboard.common.domain.BaseTimeEntity;
import com.example.jpaboard.post.domain.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String hobby;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    public User(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }
}
