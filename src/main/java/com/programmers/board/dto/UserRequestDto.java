package com.programmers.board.dto;

import com.programmers.board.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
    private final String name;
    private final Integer age;
    private final String hobby;
}
