package com.prgrms.springboard.user.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.prgrms.springboard.global.common.BaseEntity;
import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.user.domain.vo.Age;
import com.prgrms.springboard.user.domain.vo.Hobby;
import com.prgrms.springboard.user.domain.vo.Name;

import lombok.Getter;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Embedded
    private Hobby hobby;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String createdBy, LocalDateTime createdAt, Long id, Name name, Age age, Hobby hobby) {
        super(createdBy, createdAt);
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User of(String name, int age, String hobby) {
        Name userName = new Name(name);
        Age userAge = new Age(age);
        Hobby userHobby = new Hobby(hobby);
        return new User(name, LocalDateTime.now(), null, userName, userAge, userHobby);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public boolean isSame(User user) {
        return this.id.equals(user.getId());
    }
}
