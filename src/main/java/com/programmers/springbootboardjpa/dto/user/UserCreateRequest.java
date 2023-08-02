package com.programmers.springbootboardjpa.dto.user;

import com.programmers.springbootboardjpa.domain.user.Hobby;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotNullGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.PositiveGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.SizeCheckGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {

    @NotBlank(message = "사용자 이름은 Null이거나, 공백 또는 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 2, max = 30, message = "사용자 이름은 2글자 이상, 30글자 미만이어야합니다.", groups = SizeCheckGroup.class)
    private String name;

    @NotNull(message = "나이는 값이 Null일 수 없습니다", groups = NotNullGroup.class)
    @Positive(message = "나이는 숫자여야하며 양수여야합니다.", groups = PositiveGroup.class)
    private Integer age;

    @NotNull(message = "취미는 값이 Null일 수 없으며 목록에 있는 값이어야합니다.")
    private Hobby hobby;

    @Builder
    public UserCreateRequest(String name, Integer age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

}
