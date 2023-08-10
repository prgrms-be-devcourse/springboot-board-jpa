package com.programmers.springbootboardjpa.global.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Hobby {
    JIU_JIT_SU("주짓수"),
    BASKETBALL("농구"),
    FOOTBALL("축구");

    private final String value;

    private static final Map<String, Hobby> HOBBY_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(Hobby::getValue, Function.identity())));

    @JsonCreator
    public static Hobby from(String inputHobby) {
        if (!HOBBY_TYPE_MAP.containsKey(inputHobby)) {
            throw new NoSuchElementException("입력하신 취미는 목록에 존재하지 않습니다.");
        }

        return HOBBY_TYPE_MAP.get(inputHobby);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
