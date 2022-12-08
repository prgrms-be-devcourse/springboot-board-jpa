package com.prgrms.work.post.domain;

import com.prgrms.work.common.BaseEntity;
import com.prgrms.work.error.EntityInvalidException;
import com.prgrms.work.user.domain.User;
import java.text.MessageFormat;
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
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {}

    protected Post(String createdBy, String title, String content, User user) {
        super(createdBy, LocalDateTime.now());
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post create(String createdBy, String title, String content, User user) {
        verifyTitleAndContentValid(title, content);

        return new Post(createdBy, title, content, user);
    }

    public Long getId() {
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
        verifyTitleAndContentValid(title, content);

        this.title = title;
        this.content = content;
    }

    private static void verifyTitleAndContentValid(String title, String content) {
        if (Objects.isNull(title) || Objects.isNull(content)) {
            throw new EntityInvalidException("제목과 내용 작성은 필수입니다.");
        }

        if (title.length() < 2 || title.length() > 30) {
            throw new EntityInvalidException(
                MessageFormat.format("제목의 길이는 3~30글자만 입력이 가능합니다. [입력하신 글자길이 : {0}]", title.length())
            );
        }

        if (content.length() < 10 || content.length() > 1000) {
            throw new EntityInvalidException(
                MessageFormat.format("내용 길이는 11~1000글자만 입력이 가능합니다. [입력하신 글자길이 : {0}]", content.length())
            );
        }
    }

}
