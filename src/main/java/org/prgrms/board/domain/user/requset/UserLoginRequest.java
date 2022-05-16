package org.prgrms.board.domain.user.requset;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserLoginRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
