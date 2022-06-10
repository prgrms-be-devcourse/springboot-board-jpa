package org.programmers.board.dto;

import org.programmers.board.exception.EmptyContentException;
import org.programmers.board.exception.EmptyTitleException;

public class BoardCreateRequest {

    private String title;
    private String content;
    private UserDTO userDTO;

    public BoardCreateRequest(String title, String content, UserDTO userDTO) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
        this.userDTO = userDTO;
    }

    private void validateContent(String content) {
        if (content.isBlank()) {
            throw new EmptyContentException("내용을 입력해주세요.");
        }
    }

    private void validateTitle(String title) {
        if (title.isBlank()) {
            throw new EmptyTitleException("제목은 공백일 수 없습니다.");
        }
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