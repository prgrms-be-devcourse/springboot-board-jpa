package com.programmers.springbootboardjpa.domain.user;

public enum Hobby {

    EXERCISE("운동"),
    READING("독서"),
    VIEWING("시청"),
    TRAVEL("여행"),
    GAME("게임");

    private final String hobbyName;

    Hobby(String hobbyName) {
        this.hobbyName = hobbyName;
    }
    
}
