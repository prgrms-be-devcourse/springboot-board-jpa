package com.programmers.domain.user.application;

import com.programmers.domain.user.ui.dto.UserDto;

public interface UserService {

    public Long createUser(UserDto userDto);

    public UserDto findUser(Long userId);
}
