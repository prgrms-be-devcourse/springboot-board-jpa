package com.prgrms.springbootboardjpa.user.entity;

import com.prgrms.springbootboardjpa.DatetimeEntity;
import com.prgrms.springbootboardjpa.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends DatetimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(length = 50)
    private String hobby;

    @Embedded
    private Name name;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    //기본 LAZY
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();

    public User(Long id, String nickName, int age, String hobby, Name name, String password, String email, List<Post> posts) {
        this.id = id;
        this.nickName = nickName;
        this.age = age;
        this.hobby = hobby;
        this.name = name;
        this.password = password;
        this.email = email;
        this.posts = posts;
    }

    public User(Long id, String nickName, int age, String hobby, Name name, String password, String email, List<Post> posts, String createdBy, LocalDateTime createdAt) {
        super(createdBy, createdAt);
        this.id = id;
        this.nickName = nickName;
        this.age = age;
        this.hobby = hobby;
        this.name = name;
        this.password = password;
        this.email = email;
        this.posts = posts;
    }

    public void addPost(Post post){
        post.setUser(this);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder{
        private Long id;
        private String nickName;
        private int age;
        private String hobby;
        private Name name;
        private String password;
        private String email;
        private List<Post> posts = new ArrayList<>();
        private String createdBy;
        private LocalDateTime createdAt;

        public UserBuilder id(Long id){
            this.id = id;
            return this;
        }

        public UserBuilder nickName(String nickName){
            this.nickName = nickName;
            return this;
        }

        public UserBuilder age(int age){
            this.age = age;
            return this;
        }

        public UserBuilder hobby(String hobby){
            this.hobby = hobby;
            return this;
        }

        public UserBuilder name(Name name){
            this.name = name;
            return this;
        }

        public UserBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserBuilder posts(List<Post> posts){
            this.posts = posts;
            return this;
        }

        public UserBuilder createdBy(String createdBy){
            this.createdBy = createdBy;
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public User build(){
            return new User(this.id, this.nickName, this.age, this.hobby, this.name, this.password, this.email, this.posts,this.createdBy,this.createdAt);
        }
    }
}
