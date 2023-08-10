package com.programmers.springbootboardjpa.dto.user;

import com.programmers.springbootboardjpa.dto.validate.NameSize;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.dto.validate.ValidationGroups.NotNullGroup;
import com.programmers.springbootboardjpa.global.enums.Hobby;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@NameSize(name = "name")
public class UserUpdateRequest {

    @NotBlank(message = "사용자 이름은 Null이거나, 공백 또는 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    private String name;

    @NotNull(message = "취미는 값이 Null일 수 없으며 목록에 있는 값이어야합니다.", groups = NotNullGroup.class)
    private Hobby hobby;

    @Builder
    public UserUpdateRequest(String name, Hobby hobby) {
        this.name = name;
        this.hobby = hobby;
    }
}
