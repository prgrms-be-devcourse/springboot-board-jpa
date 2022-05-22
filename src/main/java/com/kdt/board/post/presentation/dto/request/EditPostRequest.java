package com.kdt.board.post.presentation.dto.request;

import com.kdt.board.post.application.dto.request.EditPostRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EditPostRequest {

    @NotNull
    @Positive
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private EditPostRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public EditPostRequestDto toRequestDto(Long postId) {
        return EditPostRequestDto.builder()
                .userId(getUserId())
                .postId(postId)
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
