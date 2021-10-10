package com.example.boardbackend.dto;

import com.example.boardbackend.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String password;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdAt;
}
