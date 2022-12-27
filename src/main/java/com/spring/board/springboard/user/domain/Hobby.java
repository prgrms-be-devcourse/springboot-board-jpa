package com.spring.board.springboard.user.domain;

public enum Hobby {
    READ_BOOK("책읽기"),
    LISTEN_MUSIC("음악듣기"),
    DO_EXERCISE("운동하기"),
    SLEEP("잠자기");

    private final String description;

    Hobby(String description) {
        this.description = description;
    }
}
