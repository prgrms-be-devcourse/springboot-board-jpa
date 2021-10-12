package com.example.boardbackend.dto.request;

import lombok.*;

@Getter
//@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
