package com.example.jpaboard.user.dto;

import com.example.jpaboard.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {
    private final Long id;
    private final String name;
    private final String hobby;
}
