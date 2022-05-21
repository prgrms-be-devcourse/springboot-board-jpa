package com.prgrms.boardapp.model;

import com.prgrms.boardapp.constant.PostErrMsg;
import com.prgrms.boardapp.util.CommonValidate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private Long id;
        private String title;
        private String content;
        private CommonEmbeddable commonEmbeddable;
        private User user;

        PostBuilder() {
        }

        public PostBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PostBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public PostBuilder commonEmbeddable(final CommonEmbeddable commonEmbeddable) {
            this.commonEmbeddable = commonEmbeddable;
            return this;
        }

        public PostBuilder user(final User user) {
            this.user = user;
            return this;
        }

        public Post build() {
            return new Post(this.id, this.title, this.content, this.commonEmbeddable, this.user);
        }
    }
}
