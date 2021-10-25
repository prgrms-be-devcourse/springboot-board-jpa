package com.prgrms.board.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private Long id;

    @NotBlank(message = "enter the title.")
    @Length(max = 50)
    private String title;

    @NotBlank(message = "enter the content.")
    @Length(max = 500)
    private String content;

    @NotBlank(message = "enter the user.")
    private UserDto userDto;

    @Builder
    public PostDto(Long id, String title, String content, UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
