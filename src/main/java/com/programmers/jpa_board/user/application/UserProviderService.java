package com.programmers.jpa_board.user.application;

import com.programmers.jpa_board.user.domain.dto.response.UserResponse;

public interface UserProviderService {
    UserResponse getUser(Long id);
}
