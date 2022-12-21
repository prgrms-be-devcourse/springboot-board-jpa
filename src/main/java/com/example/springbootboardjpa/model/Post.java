package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public Post(String title, String content, User user) {
        this.title = blankCheckTitle(title);
        this.content = content;
        setUser(user);
    }

    private void setUser(User user){
        this.user = user;
        user.getPosts().add(this);
    }

    private String blankCheckTitle(String title){
        if(title.isBlank())
            return "(제목없음)";
        return title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeTitle(String title){
        this.title = title;
    }
}
