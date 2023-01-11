package com.example.board.domain.post.entity;

import static com.example.board.domain.post.dto.PostDto.*;

import com.example.board.domain.post.validator.PostValidator;
import com.example.board.domain.user.entity.User;
import com.example.board.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    private static final PostValidator validator = new PostValidator();

    @Id @GeneratedValue
    @Column(name = "post_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        validator.validateTitle(title);
        validator.validateContent(content);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updatePost(UpdatePostRequest updatePostRequest) {
        validator.validateTitle(title);
        validator.validateContent(content);
        this.title = updatePostRequest.title();
        this.content = updatePostRequest.content();
    }
}
