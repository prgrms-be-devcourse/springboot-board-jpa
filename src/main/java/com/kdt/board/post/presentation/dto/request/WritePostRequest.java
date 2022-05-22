package com.kdt.board.post.presentation.dto.request;

import com.kdt.board.post.application.dto.request.WritePostRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class WritePostRequest {

    @NotNull
    @Positive
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private WritePostRequest() {
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

    public WritePostRequestDto toRequestDto() {
        return WritePostRequestDto.builder()
                .userId(getUserId())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
