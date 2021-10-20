package spring.jpa.board.dto.user;

import org.springframework.stereotype.Component;
import spring.jpa.board.domain.User;

@Component
public class UserConverter {

  //dto -> entity
  public User convertToUser(UserDto userDto) {
    User user = User.builder().id(userDto.getId())
        .name(userDto.getName())
        .age(userDto.getAge())
        .hobby(userDto.getHobby())
        .build();
    user.setCreatedAt(userDto.getCreatedAt());
    user.setCreatedBy(userDto.getCreatedBy());
    return user;
  }


  //entity -> dto
  public UserDto convertToUserDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .age(user.getAge())
        .hobby(user.getHobby())
        .createdAt(user.getCreatedAt())
        .createdBy(user.getCreatedBy())
        .build();
  }
}
