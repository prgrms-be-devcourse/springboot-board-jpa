package com.example.boardbackend.dto;

import com.example.boardbackend.domain.embeded.Email;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdAt;
}
