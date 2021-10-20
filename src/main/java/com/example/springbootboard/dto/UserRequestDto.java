package com.example.springbootboard.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class UserRequestDto {
    @NotBlank(message = "유저의 이름이 있어야합니다.")
    String name;
    @NotNull(message = "유저의 나이가 있어야합니다.")
    @PositiveOrZero(message = "나이는 0 이상 이여야합니다.")
    Integer age;
    String hobby;
}
