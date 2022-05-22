package com.kdt.board.user.application;

import com.kdt.board.user.application.dto.request.RegistrationUserRequestDto;

public interface UserService {
    void register(RegistrationUserRequestDto registrationUserRequestDto);
}
