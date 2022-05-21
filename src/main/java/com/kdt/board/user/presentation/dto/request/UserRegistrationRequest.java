package com.kdt.board.user.presentation.dto.request;

import com.kdt.board.user.application.dto.request.UserRegistrationRequestDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class UserRegistrationRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @Min(0)
    private int age;

    private String hobby;

    protected UserRegistrationRequest() {
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

    public UserRegistrationRequestDto toRequestDto() {
        return new UserRegistrationRequestDto.Builder()
                .name(this.name)
                .email(this.email)
                .age(this.age)
                .hobby(this.hobby)
                .build();
    }
}
