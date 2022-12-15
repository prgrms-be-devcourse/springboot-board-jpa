package com.assignment.board.dto.user;

import com.assignment.board.entity.Hobby;
import lombok.*;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private int age;
    private Hobby hobby;
}
