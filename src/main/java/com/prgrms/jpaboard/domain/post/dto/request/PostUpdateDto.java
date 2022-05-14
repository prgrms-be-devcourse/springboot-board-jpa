package com.prgrms.jpaboard.domain.post.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class PostUpdateDto {
    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    private String content;

    public PostUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
