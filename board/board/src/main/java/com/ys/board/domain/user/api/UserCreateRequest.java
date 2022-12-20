package com.ys.board.domain.user.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserCreateRequest {

    @NotBlank @Size(min = 1)
    private String name;

    @NotNull @Min(1)
    private Integer age;

    private String hobby;

}
