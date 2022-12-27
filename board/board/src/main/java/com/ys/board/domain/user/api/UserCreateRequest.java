package com.ys.board.domain.user.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserCreateRequest {

    @NotBlank(message = "이름을 입력해주세요.") @Size(min = 1, message = "최소 1글자 이상이여야 합니다.")
    private String name;

    @Min(value = 1, message = "최소 1살 이상 이여야 합니다.")
    private int age;

    private String hobby;

}
