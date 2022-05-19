package com.kdt.jpaboard.domain.board.user.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private int age;
    private String hobby;
}
