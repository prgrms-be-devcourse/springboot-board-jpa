package com.spring.board.springboard.post.domain;

import com.spring.board.springboard.user.domain.User;
import com.spring.board.springboard.user.exception.AuthenticationException;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {

    private static final Integer NOTHING = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validate(title);
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(Objects.nonNull(this.user)){
            throw new AuthenticationException("작성자는 변경될 수 없습니다.");
        }
        this.user = user;
        user.getPostList().add(this);
    }

    public void changeTitle(String changeTitle) {
        validate(changeTitle);
        this.title = changeTitle;
    }

    public void changeContent(String changeContent) {
        validate(changeContent);
        this.content = changeContent;
    }

    private void validate(String input) {
        if (Objects.equals(input.length(), NOTHING)) {
            throw new IllegalArgumentException("빈 값일 수 없습니다. 반드시 입력해야합니다.");
        }
    }

}
