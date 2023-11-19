package com.kdt.simpleboard.user.dto;

import com.kdt.simpleboard.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.kdt.simpleboard.user.dto.UserResponse.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static SignUpRes toSignUpRes(User user){
        return new SignUpRes(user.getId());
    }

    public static User toUser(UserRequest.SignUpReq request){
        return User.builder()
                .name(request.name())
                .hobby(request.hobby())
                .age(request.age()).build();
    }
}
