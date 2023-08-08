package com.blackdog.springbootBoardJpa.domain.user.service.converter;

import com.blackdog.springbootBoardJpa.domain.user.model.User;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserCreateRequest;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponse;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponses;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserServiceConverter {

    public User toEntity(UserCreateRequest dto) {
        return User.builder()
                .name(dto.name())
                .age(dto.age())
                .hobby(dto.hobby())
                .build();
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName().getNameValue(),
                user.getAge().getAgeValue(),
                user.getHobby(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserResponses toResponses(Page<User> users) {
        return new UserResponses(users.map(this::toResponse));
    }

}
