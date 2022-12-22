package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.CreateUserResponse;
import com.prgrms.jpa.controller.dto.user.GetByIdUserResponse;
import com.prgrms.jpa.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.jpa.utils.UserEntityDtoMapper.toUserDto;
import static com.prgrms.jpa.utils.UserEntityDtoMapper.toUserIdDto;

@Service
public class UserFacade {

    private final UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
        User user = userService.create(createUserRequest);
        return toUserIdDto(user.getId());
    }

    @Transactional(readOnly = true)
    public GetByIdUserResponse getById(long id) {
        User user = userService.getById(id);
        return toUserDto(user);
    }
}
