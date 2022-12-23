package com.kdt.springbootboardjpa.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostRequest {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(min = 1, max = 30, message = "제목을 최소 {min} 이상 {max}이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public CreatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
