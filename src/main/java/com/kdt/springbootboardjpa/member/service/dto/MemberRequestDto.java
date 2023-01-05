package com.kdt.springbootboardjpa.member.service.dto;

import com.kdt.springbootboardjpa.member.domain.Hobby;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Getter
@NoArgsConstructor  // 직렬화, 역직렬화 -> jackson(기본 생성자) 필요 -> public
public class MemberRequestDto {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 10, message = "이름을 최소 {min}글자 이상 {max}글자 이하로 입력해주세요.")
    private String name;

    @NotNull(message = "나이는 필수 입력값입니다.")
    @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
    private int age;

    @Enum(enumClass = Hobby.class, ignoreCase = true, message = "Hobby Enum값이 존재하지 않습니다.")
    private Hobby hobby;

    @Builder
    public MemberRequestDto(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}