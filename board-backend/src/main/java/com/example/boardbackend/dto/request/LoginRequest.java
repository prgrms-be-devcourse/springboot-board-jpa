package com.example.boardbackend.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank()
    private String email;
    private String password;
}
