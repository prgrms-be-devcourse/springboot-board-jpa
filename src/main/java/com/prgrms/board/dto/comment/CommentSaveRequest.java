package com.prgrms.board.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveRequest {
    @NotNull(message = "유저 Id는 필수 요소입니다.")
    @Min(value = 1, message = "유저 Id는 1 이상이어야 합니다.")
    private Long userId;

    @NotNull(message = "게시글 Id는 필수 요소입니다.")
    @Min(value = 1, message = "게시글 Id는 1 이상이어야 합니다.")
    private Long postId;

    @NotBlank(message = "댓글 내용은 필수 요소입니다.")
    private String content;
}
