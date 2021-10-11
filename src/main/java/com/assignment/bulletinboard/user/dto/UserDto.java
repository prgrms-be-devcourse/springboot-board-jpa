package com.assignment.bulletinboard.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    @NotBlank
    @Length(max = 20)
    private String name;

    @Min(14)
    @Max(100)
    private int age;

    private String hobby;

    private String createdBy;

    private String createdAt;
}
