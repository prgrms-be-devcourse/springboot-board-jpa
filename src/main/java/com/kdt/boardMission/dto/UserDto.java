package com.kdt.boardMission.dto;

import com.kdt.boardMission.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private int age;
    private String hobby;

    public static User convertUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getAge(), userDto.getHobby());
    }

    public static UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
