package com.misson.jpa_board.dto;

import com.misson.jpa_board.domain.Hobby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private Hobby hobby;
}
