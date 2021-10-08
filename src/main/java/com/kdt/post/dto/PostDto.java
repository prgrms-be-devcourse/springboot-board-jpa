package com.kdt.post.dto;

import com.kdt.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private UserDto userDto;

}
