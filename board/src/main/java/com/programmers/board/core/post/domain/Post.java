package com.programmers.board.core.post.domain;


import com.programmers.board.core.common.entity.BaseEntity;
import com.programmers.board.core.user.domain.User;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post(){}

    public Post(String title, String content, User user) {
        validateTitle(title);
        validateContent(content);
        validateUser(user);

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

    // Validate Logic
    private void validateTitle(String title){
        Assert.isTrue(title.length() > 0 && title.length() <= 50, "제목의 길이는 50자 이하(필수)입니다.");
    }

    private void validateContent(String content) {
        Assert.notNull(content, "내용이 없습니다.");
    }

    private void validateUser(User user) {
        Assert.notNull(user, "사용자를 확인 할 수 없습니다.");
    }
}
