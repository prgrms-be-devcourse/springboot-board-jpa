package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.CreateUserRequest;

public interface UserService {

    long create(CreateUserRequest createUserRequest);
}
