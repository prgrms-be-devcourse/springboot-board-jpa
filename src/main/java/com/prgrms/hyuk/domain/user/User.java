package com.prgrms.hyuk.domain.user;

import com.prgrms.hyuk.domain.BaseEntity;
import com.prgrms.hyuk.domain.post.Post;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @Embedded
    private Posts posts = new Posts();

    protected User() {
    }

    public User(Name name, Age age, Hobby hobby) {
        super("");
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    /*연관관계 편의 메서드*/
    public void addPost(Post post) {
        post.assignUser(this);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public Posts getPosts() {
        return posts;
    }
}
