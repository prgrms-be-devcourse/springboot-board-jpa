package com.programmers.domain.user.application;

import com.programmers.domain.user.ui.dto.UserDto;

public interface UserService {

    Long createUser(UserDto userDto);

    UserDto findUser(Long userId);
}
