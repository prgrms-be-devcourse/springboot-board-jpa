package kdt.prgms.springbootboard.converter;


import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.dto.SimpleUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public SimpleUserDto convertUserDto(User user) {
        return new SimpleUserDto(user.getId(), user.getName(), user.getAge());
    }
}
