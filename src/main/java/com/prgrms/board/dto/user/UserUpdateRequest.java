package com.prgrms.board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserUpdateRequest {

    private static final String NAME_VALIDATION_PATTERN = "^[가-힣a-zA-Z]*$";

    @Size(max = 50, message = "이름은 최대 50자까지 입력 가능합니다.")
    @Pattern(regexp = NAME_VALIDATION_PATTERN, message = "이름은 한글 또는 영문 문자만 입력 가능합니다.")
    private String name;
    @Min(value = 0, message = "사용자 나이는 0 이상이어야 합니다.")
    private Integer age;
}
