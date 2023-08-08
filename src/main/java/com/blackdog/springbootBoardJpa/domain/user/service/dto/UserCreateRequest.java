package com.blackdog.springbootBoardJpa.domain.user.service.dto;

import com.blackdog.springbootBoardJpa.domain.user.model.vo.Age;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotNull
        Name name,

        @NotNull
        Age age,

        @NotBlank
        String hobby
) {
}
