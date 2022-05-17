package com.programmers.springbootboardjpa.domain.post;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.post.response.PostResponse;
import com.programmers.springbootboardjpa.dto.post.response.UserResponse;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {
    }

    public Post(String createdBy, LocalDateTime cratedAt, String title, String content) {
        super(createdBy, cratedAt);
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }

    private void validateTitle(String title) {
        Assert.notNull(title, "Title should not be null.");
        Assert.isTrue(title.length() <= 100,
                "Title length should be less than or equal to 100 characters.");
    }

    private void validateContent(String content) {
        Assert.notNull(content, "Content should not be null.");
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
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

    public PostResponse toPostResponse() {
        return new PostResponse.PostResponseBuilder()
                .postId(id)
                .title(title)
                .content(content)
                .createdBy(getCreatedBy())
                .cratedAt(getCratedAt())
                .userResponse(new UserResponse.UserResponseBuilder()
                        .userId(user.getId())
                        .name(user.getName())
                        .age(user.getAge())
                        .hobby(user.getHobby())
                        .createdBy(user.getCreatedBy())
                        .createdAt(user.getCratedAt())
                        .build()
                ).build();
    }
}
