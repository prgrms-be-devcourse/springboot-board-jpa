package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;

public interface UserService {

    long create(CreateUserRequest createUserRequest);
}
