package com.prgrms.board.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PostUpdateDto {
    @NotNull
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
