package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.dto.CreateUserRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;

public interface UserService {

    IdResponse saveUser(CreateUserRequest request);
}
