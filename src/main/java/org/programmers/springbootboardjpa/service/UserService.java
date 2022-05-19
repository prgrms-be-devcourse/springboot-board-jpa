package org.programmers.springbootboardjpa.service;

import org.programmers.springbootboardjpa.controller.dto.UserCreateForm;
import org.programmers.springbootboardjpa.domain.User;

public interface UserService {
    Long registerUser(UserCreateForm userCreateForm);

    User findUserWith(Long userId);
}
