package com.example.springbootboardjpa.enums;

public enum Hobby {
    EXERCISE("운동"),
    GAME("게임"),
    READING("독서"),
    SINGING("노래"),
    COOKING("요리");

    private final String hobbyName;

    Hobby(String hobby) {
        this.hobbyName = hobby;
    }
}
