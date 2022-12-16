package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.user.domain.UserDTO;

public class PostDTO {
    private final String title;
    private final String content;
    private final UserDTO user;

    public PostDTO(String title, String content, UserDTO user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UserDTO getUser() {
        return user;
    }
}
