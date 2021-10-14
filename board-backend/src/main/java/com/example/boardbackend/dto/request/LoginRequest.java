package com.example.boardbackend.dto.request;

import lombok.*;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
