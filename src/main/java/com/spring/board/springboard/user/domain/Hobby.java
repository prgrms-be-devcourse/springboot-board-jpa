package com.spring.board.springboard.user.domain;

public enum Hobby {
    read_book("책읽기"),
    listen_music("음악듣기"),
    do_exercise("운동하기"),
    sleep("잠자기");

    private final String description;

    Hobby(String description) {
        this.description = description;
    }
}
