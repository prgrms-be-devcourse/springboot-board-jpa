package com.prgrms.jpa.controller.dto.post.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePostRequest {

    @NotBlank(message = "제목은 필수 입력사항입니다.")
    @Size(max = 30, message = "제목은 {max}글자 이하로 입력할 수 있습니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public UpdatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
