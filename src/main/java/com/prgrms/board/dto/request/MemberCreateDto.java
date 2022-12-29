package com.prgrms.board.dto.request;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class MemberCreateDto {
    public static final int MEMBER_NAME_MIN_LENGTH = 2;
    public static final int MEMBER_NAME_MAX_LENGTH = 20;

    @NotBlank(message = "{exception.member.name.null}")
    @Length(min = MEMBER_NAME_MIN_LENGTH,
            max = MEMBER_NAME_MAX_LENGTH,
            message = "{exception.member.name.length}" )
    private String name;

    @NotNull(message = "{exception.member.age.null}")
    @Positive(message = "{exception.member.age.positive}")
    private int age;

    private String hobby;
}
