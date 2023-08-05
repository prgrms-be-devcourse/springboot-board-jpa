package com.prgrms.board.dto.user;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {

    private static final String NAME_VALIDATION_PATTERN = "^[가-힣a-zA-Z]*$";

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank
    private String email;
    @NotBlank(message = "이름은 필수 항목입니다.")
    @Size(max = 50, message = "이름은 최대 50자까지 입력 가능합니다.")
    @Pattern(regexp = NAME_VALIDATION_PATTERN, message = "이름은 한글 또는 영문 문자만 입력 가능합니다.")
    private String name;
    @Min(value = 0, message = "사용자 나이는 0 이상이어야 합니다.")
    @NotNull
    private Integer age;
}
