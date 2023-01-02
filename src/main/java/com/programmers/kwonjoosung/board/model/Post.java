package com.programmers.kwonjoosung.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Post(String title, String content) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }

    private void validateContent(String content) {
        Assert.isTrue(content.length() <= 100, "게시글은 최대 100글자 입니다.");
    }

    private void validateTitle(String title) {
        Assert.isTrue(title.length() <= 30, "제목은 최대 30글자 입니다.");
    }

    public void setUser(User user) { // 연관관계 편의 메소드
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }


    public void changeTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void changeContent(String content) {
        validateContent(content);
        this.content = content;
    }

}
