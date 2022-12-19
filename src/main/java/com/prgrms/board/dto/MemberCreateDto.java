package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class MemberCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private int age;

    private String hobby;
}
