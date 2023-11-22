package com.example.board.model;

import com.example.board.dto.PostDto;
import com.example.board.dto.PostUpdateDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", length = 30)
    private String title;

    @Lob
    private String contents;

    @Column(name = "created_by")
    private String createdBy;

    private Post(User user, PostDto postDto) {
        postSaveValidate(user, postDto);
        this.user = user;
        this.title = postDto.title();
        this.contents = postDto.contents();
        this.createdBy = user.getName();
    }

    private static void postSaveValidate(User user, PostDto postDto) {
        if (user == null) {
            throw new BaseException(ErrorMessage.USER_NOT_FOUND);
        }
        if (postDto.title().isBlank() || (postDto.title().length() > 20)) {
            throw new BaseException(ErrorMessage.WRONG_TITLE_VALUE);
        }
        if (postDto.contents().isBlank()) {
            throw new BaseException(ErrorMessage.WRONG_CONTENTS_VALUE);
        }
        if (user.getName().isBlank()) {
            throw new BaseException(ErrorMessage.WRONG_USER_NAME);
        }
    }

    public static Post from(User user, PostDto postDto) {
        return new Post(user, postDto);
    }

    public Long update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.title();
        this.contents = postUpdateDto.contents();
        return this.id;
    }
}
