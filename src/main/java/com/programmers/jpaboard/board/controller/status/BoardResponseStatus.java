package com.programmers.jpaboard.board.controller.status;

import lombok.Getter;

@Getter
public enum BoardResponseStatus {
    BOARD_CREATION_SUCCESS("Board Creation Success"),
    BOARD_UPDATE_SUCCESS("Board Update Success"),
    BOARD_LOOKUP_SUCCESS("Board Lookup Success"),
    BOARD_LOOKUP_ALL_SUCCESS("Board Lookup All Success");

    private String message;

    BoardResponseStatus(String message) {
        this.message = message;
    }
}
