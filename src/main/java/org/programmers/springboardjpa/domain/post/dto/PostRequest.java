package org.programmers.springboardjpa.domain.post.dto;

import org.programmers.springboardjpa.domain.user.dto.UserDto.UserRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostRequest {
    public record PostCreateRequestDto(
            @NotBlank(message = "제목은 Blank 일 수 없습니다.")
            String title,
            @NotEmpty(message = "내용은 Empty 일 수 없습니다.")
            String content,
            @NotNull
            UserRequest userDto
    ) {}

    public record PostUpdateRequestDto(
            @NotBlank(message = "제목은 Blank 일 수 없습니다.")
            String title,
            @NotEmpty(message = "내용은 Empty 일 수 없습니다.")
            String content
    ) {}
}
