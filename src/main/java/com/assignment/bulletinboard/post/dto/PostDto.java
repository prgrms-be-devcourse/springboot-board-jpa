package com.assignment.bulletinboard.post.dto;

import com.assignment.bulletinboard.user.dto.UserDto;
import lombok.Builder;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDto {

    private Long id;

    @NotBlank
    @Length(max = 30)
    private String title;

    @NotBlank
    private String content;

    private String createdBy;

    private LocalDateTime createdAt;

    private UserDto userDto;
}
