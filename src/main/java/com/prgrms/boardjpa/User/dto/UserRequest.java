package com.prgrms.boardjpa.User.dto;

import com.prgrms.boardjpa.User.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class UserRequest {

    @NotBlank(message = "유저 이름을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "이름은 영어만 입력가능합니다.")
    private String name;

    @Min(value = 1, message = "올바른 나이를 입력해 주세요.")
    private int age;

    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "취미는 한글/영어만 입력가능합니다.")
    @NotBlank(message = "취미를 입력해주세요.")
    private String hobby;

    @Builder
    public UserRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}