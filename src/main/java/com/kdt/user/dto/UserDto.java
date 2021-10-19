package com.kdt.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserDto {

    private Long id;

    @NotBlank(message = "name is not blank")
    @Length(max = 30, message = "length must be between 0 and 30")
    private String name;

    @Pattern(regexp = "^(19[0-9]{2}|2[0-9]{3})$", message = "is invalid range")
    private int birthYear;

}
