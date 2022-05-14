package com.programmers.board.core.user.application.dto;

import com.programmers.board.core.user.domain.Hobby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private Hobby hobby;
}
