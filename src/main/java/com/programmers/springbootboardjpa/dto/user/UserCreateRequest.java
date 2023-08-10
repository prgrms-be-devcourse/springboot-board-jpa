package com.programmers.springbootboardjpa.dto.user;

import com.programmers.springbootboardjpa.dto.validate.NameSize;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.NotNullGroup;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.PositiveGroup;
import com.programmers.springbootboardjpa.global.enums.Hobby;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@NameSize(name = "name")//이름이 2이상 20이하인지 검사
public class UserCreateRequest {

    @NotBlank(message = "사용자 이름은 Null이거나, 공백 또는 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    private String name;

    @NotNull(message = "나이는 Null일 수 없습니다", groups = NotNullGroup.class)
    @Positive(message = "나이는 숫자여야하며 양수여야합니다.", groups = PositiveGroup.class)
    private int age;

    @NotNull(message = "취미는 값이 Null일 수 없으며 목록에 있는 값이어야합니다.", groups = NotNullGroup.class)
    private Hobby hobby;
}
