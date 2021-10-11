package com.assignment.bulletinboard.post;

import com.assignment.bulletinboard.BaseEntity;
import com.assignment.bulletinboard.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

//    public void setUser(User user) {
//        if(Objects.nonNull(this.user)){
//            this.user.getPostList().remove(this);
//        }
//        this.user = user;
//        user.getPostList().add(this);
//    }

}
