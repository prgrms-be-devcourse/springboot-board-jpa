package com.example.board.model;

import com.example.board.dto.PostCreateDto;
import com.example.board.dto.PostUpdateDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import io.micrometer.common.util.StringUtils;
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

    private Post(User user, PostCreateDto postDto) {
        postSaveValidate(user, postDto);
        this.user = user;
        this.title = postDto.title();
        this.contents = postDto.contents();
        this.createdBy = user.getName();
    }

    public static Post from(User user, PostCreateDto postDto) {
        return new Post(user, postDto);
    }

    public Long update(PostUpdateDto postUpdateDto) {
        postUpdateValidate(postUpdateDto);
        this.title = postUpdateDto.title();
        this.contents = postUpdateDto.contents();
        return this.id;
    }

    private static void postSaveValidate(User user, PostCreateDto postDto) {
        if (user == null) {
            throw new BaseException(ErrorMessage.USER_NOT_FOUND);
        }
        if (StringUtils.isBlank(postDto.title()) || (postDto.title().length() > 20)) {
            throw new BaseException(ErrorMessage.WRONG_TITLE_VALUE);
        }
        if (StringUtils.isBlank(postDto.contents())) {
            throw new BaseException(ErrorMessage.WRONG_CONTENTS_VALUE);
        }
        if (StringUtils.isBlank(user.getName())) {
            throw new BaseException(ErrorMessage.WRONG_USER_NAME);
        }
    }

    private static void postUpdateValidate(PostUpdateDto postUpdateDto) {
        if (StringUtils.isBlank(postUpdateDto.title()) || (postUpdateDto.title().length() > 20)) {
            throw new BaseException(ErrorMessage.WRONG_TITLE_VALUE);
        }
        if (StringUtils.isBlank(postUpdateDto.contents())) {
            throw new BaseException(ErrorMessage.WRONG_CONTENTS_VALUE);
        }
    }
}
