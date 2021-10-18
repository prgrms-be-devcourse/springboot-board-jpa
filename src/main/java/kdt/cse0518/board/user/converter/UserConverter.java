package kdt.cse0518.board.user.converter;

import kdt.cse0518.board.user.dto.UserDto;
import kdt.cse0518.board.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto toUserDto(final User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .posts(user.getPosts())
                .build();
    }

    public User toUser(final UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .createdAt(userDto.getCreatedAt())
                .modifiedAt(userDto.getModifiedAt())
                .posts(userDto.getPosts())
                .build();
    }
}
