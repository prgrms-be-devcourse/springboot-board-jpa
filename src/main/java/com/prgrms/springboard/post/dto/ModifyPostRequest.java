package com.prgrms.springboard.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;

@Getter
public class ModifyPostRequest {
    @NotNull
    private Long userId;

    @NotBlank(message = "제목은 비거나 공백만 있을 수 없습니다.")
    @Length(max = 30, message = "제목는 {max}글자 이하여야합니다.")
    private String title;

    @NotBlank(message = "내용은 비거나 공백만 있을 수 없습니다.")
    @Length(max = 200, message = "내용은 {max}글자 이하여야합니다.")
    private String content;

    protected ModifyPostRequest() {
    }

    public ModifyPostRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

}
