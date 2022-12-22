package com.example.board.domain.post.entity;

import com.example.board.domain.user.entity.User;
import com.example.board.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static com.example.board.domain.post.dto.PostDto.UpdatePostRequest;
import static com.example.board.global.validator.PostValidator.validateContent;
import static com.example.board.global.validator.PostValidator.validateTitle;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "{exception.entity.post.title.null}")
    @Length(min = 1, message = "{exception.entity.post.title.length}")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "{exception.entity.post.content.null}")
    @Length(min = 1, message = "{exception.entity.post.content.length}")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updatePost(UpdatePostRequest updatePostRequest) {
        validateTitle(title);
        validateContent(content);
        this.title = updatePostRequest.title();
        this.content = updatePostRequest.content();
    }
}
