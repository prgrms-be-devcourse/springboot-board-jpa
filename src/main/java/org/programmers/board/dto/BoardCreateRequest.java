package org.programmers.board.dto;

public class BoardCreateRequest {

    private String title;
    private String content;
    private UserDTO userDTO;

    public BoardCreateRequest(String title, String content, UserDTO userDTO) {
        this.title = title;
        this.content = content;
        this.userDTO = userDTO;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}