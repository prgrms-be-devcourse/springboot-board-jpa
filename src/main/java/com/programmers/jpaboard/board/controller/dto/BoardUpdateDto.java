package com.programmers.jpaboard.board.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateDto {

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    @Length(min = 1, max = 100000)
    private String content;
}
