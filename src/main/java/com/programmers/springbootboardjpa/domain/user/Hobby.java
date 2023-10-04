package com.programmers.springbootboardjpa.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Hobby {

    EXERCISE("운동"),
    READING("독서"),
    VIEWING("시청"),
    TRAVEL("여행"),
    GAME("게임");

    private final String hobbyName;
    private static final Map<String, Hobby> hobbyNames = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(Hobby::getHobbyName, Function.identity())));

    Hobby(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    @JsonCreator
    public static Hobby from(String hobbyName) {
        if (!hobbyNames.containsKey(hobbyName)) {
            throw new NoSuchElementException("입력하신 취미는 존재하지않는 취미입니다.");
        }

        return hobbyNames.get(hobbyName);
    }

    @JsonValue
    public String getHobbyName() {
        return hobbyName;
    }

}
