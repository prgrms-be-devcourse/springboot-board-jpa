package com.example.boardbackend.dto;

import com.example.boardbackend.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Long view;
    private UserDto userDto;
    private LocalDateTime createdAt;
}
