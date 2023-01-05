package com.kdt.springbootboardjpa.member.domain;

import java.util.stream.Stream;

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

    public static Hobby toMapHobby(String hobby) {  // String -> enum
        return Stream.of(Hobby.values())
                .filter(input -> input.value.equals(hobby))
                .findFirst()
                .orElse(ETC);
    }
}