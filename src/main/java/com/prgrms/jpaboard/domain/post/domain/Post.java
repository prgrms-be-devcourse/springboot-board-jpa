package com.prgrms.jpaboard.domain.post.domain;

import com.prgrms.jpaboard.domain.post.dto.response.PostDetailDto;
import com.prgrms.jpaboard.domain.post.dto.response.PostDto;
import com.prgrms.jpaboard.domain.post.util.PostValidator;
import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.global.common.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.prgrms.jpaboard.domain.post.util.PostValidator.validateContent;
import static com.prgrms.jpaboard.domain.post.util.PostValidator.validateTitle;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {
    }

    public Post(String createdBy, String title, String content) {
        super("Post", createdBy);
        validateTitle("Post", title);
        validateContent("Post", content);

        this.title = title;
        this.content = content;
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private String title;
        private String content;
        private String createdBy;

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Post build() {
            return new Post(createdBy, title, content);
        }
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

    public PostDto getPostDto() {
        return PostDto.builder()
                .id(this.id)
                .title(this.title)
                .user(this.user.getUserInfoDto())
                .createdAt(this.createdAt)
                .build();
    }

    public PostDetailDto getPostDetailDto() {
        return PostDetailDto.postDetailDtoBuilder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createdAt(this.createdAt)
                .user(this.user.getUserInfoDto())
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setUser(User user) {
        if(Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

}
