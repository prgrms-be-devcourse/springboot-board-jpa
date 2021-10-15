package com.example.board.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
public class PostRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

}
