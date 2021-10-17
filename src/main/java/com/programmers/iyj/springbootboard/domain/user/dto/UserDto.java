package com.programmers.iyj.springbootboard.domain.user.dto;

import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class UserDto {

    @NotNull
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @Min(value = 1)
    @Max(value = 150)
    private Integer age;

    private Hobby hobby;
}
