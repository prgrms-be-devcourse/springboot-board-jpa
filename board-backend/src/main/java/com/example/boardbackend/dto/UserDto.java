package com.example.boardbackend.dto;

import com.example.boardbackend.domain.embeded.Email;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Email email;
    private String password;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdAt;
}
