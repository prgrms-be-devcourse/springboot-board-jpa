package com.programmers.jpaboard.board.exception;

public class BoardNotFoundException extends IllegalArgumentException {
    public BoardNotFoundException(Long boardId) {
        super(String.format("boardId : %d", boardId));
    }
}
