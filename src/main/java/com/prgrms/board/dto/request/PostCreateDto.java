package com.prgrms.board.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PostCreateDto {

    @NotBlank(message = "exception.post.title.null")
    private String title;

    @NotBlank(message = "exception.post.content.null")
    private String content;

    @NotNull(message = "exception.post.member.null")
    private Long writerId;
}
