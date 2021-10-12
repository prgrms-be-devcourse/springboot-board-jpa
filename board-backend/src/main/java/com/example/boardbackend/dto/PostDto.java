package com.example.boardbackend.dto;

import com.example.boardbackend.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long view;
    private UserDto userDto;
    private LocalDateTime createAt;
}
