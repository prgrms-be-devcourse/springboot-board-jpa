package com.prgrms.jpa.controller.dto.post.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "제목은 필수 입력사항입니다.")
    @Size(max = 30, message = "제목은 {max}글자 이하로 입력할 수 있습니다.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @NotNull
    private final Long userId;
}
