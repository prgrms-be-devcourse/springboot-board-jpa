package com.prgrms.boardjpa.utils.converter;

import com.prgrms.boardjpa.entity.User;
import com.prgrms.boardjpa.users.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  public static User userDtoToUser(UserDto userDto) {
    return User
        .builder()
        .id(userDto.getUserId())
        .hobby(userDto.getHobby())
        .name(userDto.getName())
        .age(userDto.getAge())
        .build();
  }

  public static UserDto userToUserDto(User user) {
    return UserDto
        .builder()
        .userId(user.getId())
        .hobby(user.getHobby())
        .name(user.getName())
        .age(user.getAge())
        .build();
  }
}
