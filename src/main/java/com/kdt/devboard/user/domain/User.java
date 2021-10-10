package com.kdt.devboard.user.domain;

import com.kdt.devboard.BaseEntity;
import com.kdt.devboard.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    int age;

    @Column(name = "hobby")
    String hobby;

    @Builder
    public User(String createBy, String name, int age, String hobby) {
        super(createBy);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void changeInfo(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) { post.setUser(this); }

}
