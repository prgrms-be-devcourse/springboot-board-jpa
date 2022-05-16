package org.prgrms.board.domain.user.requset;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class UserCreateRequest {
    @NotBlank
    @Size(max = 20)
    private String firstName;
    @NotBlank
    @Size(max = 10)
    private String lastName;
    @NotNull
    private int age;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야합니다.")
    private String password;

}
