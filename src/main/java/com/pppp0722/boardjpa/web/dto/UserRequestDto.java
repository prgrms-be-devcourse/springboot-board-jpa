package com.pppp0722.boardjpa.web.dto;

import com.pppp0722.boardjpa.domain.user.User;
import java.time.LocalDateTime;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Length(min = 2, max = 50, message = "이름을 2자 이상 50자 이하로 입력해주세요.")
    private String name;

    @Positive(message = "나이를 양수로 입력해주세요.")
    private Integer age;

    @Length(min = 1, max = 50, message = "취미를 1자 이상 50자 이하로 입력해주세요.")
    private String hobby;

    public User to() {
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setName(name);
        user.setAge(age);
        user.setHobby(hobby);

        return user;
    }
}