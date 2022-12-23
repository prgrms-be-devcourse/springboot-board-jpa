package com.kdt.springbootboardjpa.domain.member;

public enum Hobby {
    GAME("GAME"),
    EXERCISE("EXERCISE"),
    MOVIE("MOVIE"),
    MUSIC("MUSIC"),
    ETC("ETC");

    private String value;

    Hobby(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}