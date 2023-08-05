package com.prgrms.board.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class CommentUpdateRequest {
    @Min(value = 1, message = "유저 Id는 1 이상이어야 합니다.")
    private final Long userId;
    @Min(value = 1, message = "게시글 Id는 1 이상이어야 합니다.")
    private final Long postId;
    @NotBlank(message = "댓글 내용은 필수 요소입니다.")
    private final String content;
}
