package com.prgrms.board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank
    private String email;
}
