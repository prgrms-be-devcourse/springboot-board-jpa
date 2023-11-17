package org.prgms.springbootboardjpayu.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record CreateUserRequest(
        @NotBlank(message = "이름을 입력해주세요.")
        @Length(min = 2, max = 30)
        String name,
        @Range(min = 0, max = 200)
        Integer age,
        @Length(max = 100)
        String hobby) {
}
