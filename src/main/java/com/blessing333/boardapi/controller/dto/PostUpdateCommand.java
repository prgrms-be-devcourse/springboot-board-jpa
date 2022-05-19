package com.blessing333.boardapi.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class PostUpdateCommand {
    @NotNull(message = "id could not be null")
    private final Long id;
    @Size(min = 2, message = "title length should over 2")
    private final String title;
    @NotBlank(message = "content could not be blank")
    private final String content;
}
