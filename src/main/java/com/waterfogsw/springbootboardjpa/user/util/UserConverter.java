package com.waterfogsw.springbootboardjpa.user.util;

import com.waterfogsw.springbootboardjpa.user.controller.dto.UserAddRequest;
import com.waterfogsw.springbootboardjpa.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toEntity(UserAddRequest userAddRequest) {
        return User.builder()
                .name(userAddRequest.name())
                .email(userAddRequest.email())
                .age(userAddRequest.age())
                .hobby(userAddRequest.hobby())
                .build();
    }
}
