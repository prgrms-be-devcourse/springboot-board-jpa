package com.prgrms.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class PostDto {
    private Long id;

    @NotBlank
    @Length(max = 50)
    private String title;

    @NotBlank
    @Length(max = 500)
    private String content;

    private UserDto userDto;

    protected PostDto() {
    }

    @Builder
    public PostDto(Long id, String title, String content, UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
