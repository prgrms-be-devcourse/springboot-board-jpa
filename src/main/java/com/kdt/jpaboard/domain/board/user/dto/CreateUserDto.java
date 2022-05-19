package com.kdt.jpaboard.domain.board.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String name;
    private int age;
    private String hobby;
}
