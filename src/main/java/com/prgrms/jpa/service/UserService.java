package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.UserIdResponse;

public interface UserService {

    UserIdResponse create(CreateUserRequest createUserRequest);
}
