package com.programmers.iyj.springbootboard.domain.user.mapper;

import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserDto userDto);
    UserDto entityToDto(User user);
}
