package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class MemberCreateDto {

    @NotBlank(message = "{exception.member.name.null}")
    @Length(min = 2, message = "{exception.member.name.length}")
    private String name;

    @NotNull(message = "{exception.member.age.null}")
    @Positive(message = "{exception.member.age.positive}")
    private int age;

    private String hobby;
}
