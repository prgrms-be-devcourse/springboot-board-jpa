package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MemberCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private int age;

    private String hobby;
}
