package com.example.spring_jpa_post.post.dto;

import com.example.spring_jpa_post.user.dto.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private UserDto userDto;
}
