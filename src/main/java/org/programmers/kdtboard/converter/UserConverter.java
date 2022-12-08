package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.dto.UserDto.Response;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public Response convertUserResponse(User user) {
        return Response.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .build();
    }
}
