package spring.jpa.board.dto.user;

import org.springframework.stereotype.Component;
import spring.jpa.board.domain.User;

@Component
public class UserConverter {

  //dto -> entity
  public User convertUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setAge(userDto.getAge());
    user.setHobby(userDto.getHobby());
    user.setCreatedAt(userDto.getCreatedAt());
    user.setCreatedBy(userDto.getCreatedBy());
    return user;
  }


  //entity -> dto
  public UserDto convertUserDto(User user) {
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
