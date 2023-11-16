package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

//TODO: dto 잘못된 값 검증해보기
public record PostDto(
        @NonNull long userId,
        @Size(max = 20) String title,
        @NotBlank String contents
) {
}
