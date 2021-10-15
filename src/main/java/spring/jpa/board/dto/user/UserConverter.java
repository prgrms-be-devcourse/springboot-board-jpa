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
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setAge(user.getAge());
    userDto.setHobby(user.getHobby());
    userDto.setCreatedAt(user.getCreatedAt());
    userDto.setCreatedBy(user.getCreatedBy());
    return userDto;
  }
}
