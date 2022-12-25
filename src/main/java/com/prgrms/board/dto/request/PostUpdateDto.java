package com.prgrms.board.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PostUpdateDto {

    @NotNull(message = "exception.post.postId.null")
    private Long postId;

    @NotBlank(message = "exception.post.title.null")
    private String title;

    @NotBlank(message = "exception.post.content.null")
    private String content;

    @NotNull
    private Long writerId;
}
