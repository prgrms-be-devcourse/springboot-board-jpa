package com.devco.jpaproject.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class PostRequestDto {
    @NotNull(message = "writer id should not be null")
    private Long writerId;

    @NotBlank(message = "title should not be blank")
    private String title;

    @NotBlank(message = "content should not be blank")
    private String content;
}
