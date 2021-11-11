package com.misson.jpa_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
public class PostCreateRequest {

    @Size(min = 1, max = 30)
    private String title;

    @Size(min = 1)
    private String content;

    @NotNull
    private Long userId;
}
