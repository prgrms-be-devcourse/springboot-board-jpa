package com.prgms.springbootboardjpa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberRequest {

    @NotBlank
    private String name;

    @Min(value = 0)
    private int age;

    private String hobby;
}
