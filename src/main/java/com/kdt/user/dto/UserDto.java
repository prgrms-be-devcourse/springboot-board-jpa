package com.kdt.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserDto {

    private Long id;

    @NotBlank
    @Length(max = 30)
    private String name;

    @Min(0)
    @Max(150)
    private int age;

}
