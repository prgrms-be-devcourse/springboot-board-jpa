package com.programmers.board.core.post.domain;


import com.programmers.board.core.common.entity.BaseEntity;
import com.programmers.board.core.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post(){}

    public Post(String title, String content, User user) {
        Assert.notNull(title, "제목을 입력하지 않았습니다.");
        Assert.isNull(content, "내용이 없습니다.");
        Assert.isNull(user, "사용자를 확인 할 수 없습니다.");

        this.title = title;
        this.content = content;
        this.user = user;
    }

    //Getter
    public Long getId(){
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    //Update
    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    //Builder
    public static PostBuilder builder(){
        return new PostBuilder();
    }

    public static class PostBuilder{

        private String title;
        private String content;
        private User user;

        public PostBuilder(){}

        public PostBuilder title(String title){
            this.title = title;
            return this;
        }

        public PostBuilder content(String content){
            this.content = content;
            return this;
        }

        public PostBuilder user(User user){
            this.user = user;
            return this;
        }

        public Post build(){
            return new Post(this.title, this.content, this.user);
        }

    }

}
