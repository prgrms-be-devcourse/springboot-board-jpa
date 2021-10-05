package com.devcourse.bbs.service.user;

import com.devcourse.bbs.controller.bind.UserCreateRequest;
import com.devcourse.bbs.controller.bind.UserUpdateRequest;
import com.devcourse.bbs.domain.user.UserDTO;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findUserByName(@NonNull String name); // NonNull to everywhere?
    UserDTO createUser(UserCreateRequest request);
    UserDTO updateUser(UserUpdateRequest request);
    void deleteUser(String name);
}
