package com.kdt.board.user.application;

import com.kdt.board.user.application.dto.request.UserRegistrationRequestDto;

public interface UserService {
    void register(UserRegistrationRequestDto userRegistrationRequestDto);
}
