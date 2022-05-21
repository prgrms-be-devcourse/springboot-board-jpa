package com.prgrms.boardapp.model;

import com.prgrms.boardapp.constants.PostErrMsg;
import com.prgrms.boardapp.utils.CommonValidate;

import javax.persistence.*;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            length = 100,
            nullable = false
    )
    private String title;
    @Lob
    @Column(
            nullable = false
    )
    private String content;
    @Embedded
    private CommonEmbeddable commonEmbeddable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public static final int TITLE_MAX_LENGTH = 100;

    protected Post() {
    }

    public Post(Long id, String title, String content, CommonEmbeddable commonEmbeddable, User user) {
        this.validateTitle(title);
        CommonValidate.validateNotNullString(content);
        this.id = id;
        this.title = title;
        this.content = content;
        this.commonEmbeddable = commonEmbeddable;
        this.user = user;
    }

    private void validateTitle(String title) {
        CommonValidate.validateNotNullString(title);
        Assert.isTrue(title.length() <= TITLE_MAX_LENGTH, PostErrMsg.TITLE_LENGTH_ERR_MSG.getMessage());
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public CommonEmbeddable getCommonEmbeddable() {
        return this.commonEmbeddable;
    }

    public User getUser() {
        return user;
    }

    public void changeUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }
}
