package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserDto {
    private int id;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdAt;
    private String createdBy;

}
