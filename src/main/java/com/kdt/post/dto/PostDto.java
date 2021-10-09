package com.kdt.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdt.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private Long id;

    private String title;

    private String content;

    @JsonProperty(value = "user")
    private UserDto userDto;

}
