package com.example.board.domain.user.service;

import static com.example.board.domain.user.dto.UserDto.*;

public interface UserService {
    SingleUserDetailResponse enroll(CreateUserRequest createUserRequest);
}
