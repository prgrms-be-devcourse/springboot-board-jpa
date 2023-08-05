package com.prgrms.board.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostSaveRequest {
    @NotNull(message = "유저 Id는 필수 요소입니다.")
    @Min(value = 1, message = "유저 Id는 1 이상이어야 합니다.")
    private Long userId;
    @NotBlank(message = "게시물 제목은 필수 요소입니다.")
    private String title;
    @NotBlank(message = "게시글 내용은 필수 요소입니다.")
    private String content;
}
