package com.prgrms.work.post.domain;

import com.prgrms.work.common.BaseEntity;
import com.prgrms.work.user.domain.User;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {}

    public Post(String createdBy, String title, String content, User user) {
        super(createdBy, LocalDateTime.now());
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post create(String createdBy, String title, String content, User user) {
        verifyValid(title, content);

        return new Post(createdBy, title, content, user);
    }

    public long getId() {
        return id;
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

    public void modify(String title, String content) {
        verifyValid(title, content);

        this.title = title;
        this.content = content;
    }

    private static void verifyValid(String title, String content) {
        if (Objects.isNull(title) || Objects.isNull(content)) {
            throw new IllegalArgumentException("제목과 내용 작성은 필수입니다.");
        }

        if (title.length() < 2 || title.length() > 30) {
            throw new IllegalArgumentException("제목의 길이는 3~30글자만 입력이 가능합니다. 입력하신 글자길이 : " + title.length());
        }

        if (content.length() < 10 || content.length() > 1000) {
            throw new IllegalArgumentException("내용 길이는 11~1000글자만 입력이 가능합니다. 입력하신 글자길이 : " + content.length());
        }
    }

}
