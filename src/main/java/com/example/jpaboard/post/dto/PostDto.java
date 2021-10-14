package com.example.jpaboard.post.dto;


import com.example.jpaboard.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostDto {
    private final Long id;
    private final String title;
    private final String content;
    private final UserDto author;
}
