package com.misson.jpa_board.dto;

import com.misson.jpa_board.domain.Hobby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;

    @Size(min = 1, max = 20)
    private String name;

    @Min(0)
    private int age;

    @Size(min = 1, max = 30)
    private Hobby hobby;
}
