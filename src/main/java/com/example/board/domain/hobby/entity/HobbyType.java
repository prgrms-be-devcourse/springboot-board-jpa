package com.example.board.domain.hobby.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HobbyType {

    GAME("게임");

    private final String type;

    private static final Map<String, HobbyType> HOBBY_MAP = new HashMap<>();

    static {
        Arrays.stream(HobbyType.values())
                .forEach(hobbyType -> HOBBY_MAP.put(hobbyType.type, hobbyType));
    }

    @JsonCreator
    public static HobbyType from(String type) {
        return Optional.ofNullable(HOBBY_MAP.get(type))
                .orElseThrow(() -> new IllegalArgumentException("exception.entity.hobby.type"));
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
