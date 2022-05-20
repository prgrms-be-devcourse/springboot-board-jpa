package com.prgrms.boardapp.model;

import com.prgrms.boardapp.constants.PostErrMsg;
import com.prgrms.boardapp.utils.CommonValidate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.springframework.util.Assert;

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

    public static final int TITLE_MAX_LENGTH = 100;

    protected Post() {
    }

    public Post(Long id, String title, String content, CommonEmbeddable commonEmbeddable) {
        this.validateTitle(title);
        CommonValidate.validateNotNullString(content);
        this.id = id;
        this.title = title;
        this.content = content;
        this.commonEmbeddable = commonEmbeddable;
    }

    private void validateTitle(String title) {
        CommonValidate.validateNotNullString(title);
        Assert.isTrue(title.length() <= TITLE_MAX_LENGTH, PostErrMsg.TITLE_LENGTH_ERR_MSG.getMessage());
    }

    public static PostBuilder builder() {
        return new PostBuilder();
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

    public static class PostBuilder {
        private Long id;
        private String title;
        private String content;
        private CommonEmbeddable commonEmbeddable;

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

        public Post build() {
            return new Post(this.id, this.title, this.content, this.commonEmbeddable);
        }

        public String toString() {
            return "PostBuilder(id=" + this.id + ", title=" + this.title + ", content=" + this.content + ", commonEmbeddable=" + this.commonEmbeddable + ")";
        }
    }
}
