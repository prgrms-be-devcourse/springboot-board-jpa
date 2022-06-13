package com.su.gesipan.user;

import com.su.gesipan.post.PostDtoMapper;
import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class UserDtoMapper {
    public static User toUserEntity(UserDto.Create dto) {
        return User.of(dto.getName(), dto.getAge(), dto.getHobby());
    }

    public static UserDto.Result toUserResult(User user) {
        var postResultDto = user.getPosts().stream()
                .map(PostDtoMapper::toPostResult)
                .collect(toList());
        return UserDto.Result.of(user.getId(), user.getName(), user.getAge(), user.getHobby(), postResultDto);
    }
}
