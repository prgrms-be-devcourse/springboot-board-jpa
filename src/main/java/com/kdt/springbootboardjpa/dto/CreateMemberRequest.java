package com.kdt.springbootboardjpa.dto;

import com.kdt.springbootboardjpa.domain.member.Hobby;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMemberRequest {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 10, message = "이름을 최소 {min}글자 이상 {max}글자 이하로 입력해주세요.")
    private String name;

    @NotNull(message = "나이는 필수 입력값입니다.")
    @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
    private int age;

    @Enum(enumClass = Hobby.class, ignoreCase = true, message = "Hobby Enum값이 존재하지 않습니다.")
    private String hobby;

    @Builder
    public CreateMemberRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}