package com.prgrms.jpa.controller.dto.post;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UpdatePostRequest {

    @NotBlank(message = "제목은 필수 입력사항입니다.")
    @Size(max = 30, message = "제목은 {max}글자 이하로 입력할 수 있습니다.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @Builder
    public UpdatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
