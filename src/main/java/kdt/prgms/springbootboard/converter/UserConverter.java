package kdt.prgms.springbootboard.converter;


import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convertUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge());
    }

    public User convertUser(UserDto userDto) {
        return User.createUser(userDto.getName(), userDto.getAge());
    }
}
