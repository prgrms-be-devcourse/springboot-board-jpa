package com.kdt.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private Long id;

    @NotNull(message = "Title is mandatory")
    @Size(max = 100)
    private String title;

    @NotNull(message = "Content is mandatory")
    private String content;

    @NotNull
    @JsonProperty(value = "user")
    private UserDto userDto;

    @Builder
    public PostDto(Long id, String title, String content, UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
