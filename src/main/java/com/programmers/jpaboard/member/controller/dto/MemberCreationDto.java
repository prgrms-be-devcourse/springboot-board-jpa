package com.programmers.jpaboard.member.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberCreationDto {
    @NotBlank
    @Length(min = 1, max = 10)
    private String name;

    @Range(min = 10, max = 100)
    private int age;

    @NotNull
    private List<String> hobbies;
}
