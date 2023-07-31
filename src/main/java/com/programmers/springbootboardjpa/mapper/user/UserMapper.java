package com.programmers.springbootboardjpa.mapper.user;

import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.UserCreateRequest;
import com.programmers.springbootboardjpa.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserCreateRequest userCreateRequest);

    UserResponse toUserResponse(User user);

}
