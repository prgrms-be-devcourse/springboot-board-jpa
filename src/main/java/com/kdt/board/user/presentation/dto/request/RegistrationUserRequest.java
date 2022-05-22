package com.kdt.board.user.presentation.dto.request;

import com.kdt.board.user.application.dto.request.RegistrationUserRequestDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RegistrationUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @Min(0)
    private int age;

    private String hobby;

    private RegistrationUserRequest() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public RegistrationUserRequestDto toRequestDto() {
        return RegistrationUserRequestDto.builder()
                .name(getName())
                .email(getEmail())
                .age(getAge())
                .hobby(getHobby())
                .build();
    }
}
