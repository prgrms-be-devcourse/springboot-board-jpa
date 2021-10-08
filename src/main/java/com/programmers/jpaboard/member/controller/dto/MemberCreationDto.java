package com.programmers.jpaboard.member.controller.dto;

import com.programmers.jpaboard.member.domain.vo.Age;
import com.programmers.jpaboard.member.domain.vo.Name;
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
    @Length(min = Name.MIN_LENGTH, max = Name.MAX_LENGTH)
    private String name;

    @Range(min = Age.MIN_AGE, max = Age.MAX_AGE)
    private int age;

    @NotNull
    private List<String> hobbies;
}
