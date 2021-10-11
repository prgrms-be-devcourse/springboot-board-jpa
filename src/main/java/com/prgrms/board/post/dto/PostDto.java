package com.prgrms.board.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
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

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
