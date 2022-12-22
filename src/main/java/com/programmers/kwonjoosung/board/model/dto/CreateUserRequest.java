package com.programmers.kwonjoosung.board.model.dto;

import com.programmers.kwonjoosung.board.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @PositiveOrZero(message = "나이는 0 이상의 숫자여야 합니다.")
    private Integer age;

    private String hobby;

    public User toEntity() {
        return new User(name, age, hobby);
    }
}
