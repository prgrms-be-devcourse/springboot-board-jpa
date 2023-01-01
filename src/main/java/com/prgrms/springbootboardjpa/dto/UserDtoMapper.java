package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public User convertUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        user.setHobby(userRequest.getHobby());

        return user;
    }

    public UserResponse convertUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdBy(user.getCreatedBy())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
