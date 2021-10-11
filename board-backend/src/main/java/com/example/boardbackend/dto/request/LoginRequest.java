package com.example.boardbackend.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
