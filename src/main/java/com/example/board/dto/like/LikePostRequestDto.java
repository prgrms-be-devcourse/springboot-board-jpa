package com.example.board.dto.like;

import com.example.board.contant.LikeStatus;
import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.PostLike;
import com.example.board.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikePostRequestDto {
    @NotNull
    private Long postId;
    @NotNull
    private Long userId;
    @NotNull
    @Range(min = 0, max = 1, message = "값은 0에서 1 사이의 정수만 가능합니다")
    private Integer likeType;

    public PostLike toEntity(User user, Post post) {
        return PostLike.builder()
                .post(post)
                .user(user)
                .likeStatus(LikeStatus.fromValue(likeType))
                .build();
    }
}
