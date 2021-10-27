package kdt.prgrms.devrun.user.dto;

import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter @Setter
@Builder
public class AddUserRequestDto {

    @NotBlank(message = "Id는 필수 입력값입니다.")
    private String loginId;

    @NotBlank(message = "Password는 필수 입력값입니다.")
    private String loginPw;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Min(0)
    @NotBlank(message = "나이는 필수 입력값입니다.")
    private int age;

    @Email
    @NotBlank(message = "Email은 필수 입력값입니다.")
    private String email;

    public User convertToEntity() {
        return User.builder()
            .loginId(loginId)
            .loginPw(loginPw)
            .name(name)
            .age(age)
            .email(email)
            .build();
    }

}
