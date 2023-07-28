package com.prgms.jpaBoard.domain.user;

public enum HobbyType {

    GAME("게임"),
    CLIMBING("등산"),
    RUNNING("조깅"),
    EXERCISE("운동"),
    READING("독서"),
    STUDY("공부"),
    DRINKING("술");

    private final String hobbyDescription;

    HobbyType(String hobbyDescription) {
        this.hobbyDescription = hobbyDescription;
    }
}
