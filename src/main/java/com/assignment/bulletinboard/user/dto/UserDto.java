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

    @NotBlank(message = "이름은 비워둘 수 없습니다.")
    @Length(max = 20, message = "이름은 최대 20자를 넘을 수 없습니다.")
    private String name;

    @Min(value = 14, message = "나이는 14미만일 수 없습니다.")
    @Max(value = 100, message = "나이는 100을 넘을 수 없습니다.")
    private int age;

    private String hobby;

    private String createdBy;

    private String createdAt;
}
