package com.programmers.springboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;
    private String createdBy;
}
