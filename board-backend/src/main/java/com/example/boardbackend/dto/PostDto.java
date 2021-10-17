package com.example.boardbackend.dto;

import com.example.boardbackend.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;

    @Size(message="제목은 최대 45자까지 입력 가능합니다", max=45)
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Long view;
    private UserDto userDto;
    private LocalDateTime createdAt;
}
