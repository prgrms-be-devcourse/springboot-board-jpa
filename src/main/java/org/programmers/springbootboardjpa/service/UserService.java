package org.programmers.springbootboardjpa.service;

import org.programmers.springbootboardjpa.controller.dto.UserCreateForm;
import org.programmers.springbootboardjpa.controller.dto.UserUpdateForm;
import org.programmers.springbootboardjpa.controller.dto.UserUpdateFormV1;
import org.programmers.springbootboardjpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Long registerUser(UserCreateForm userCreateForm);

    User findUserWith(Long userId);

    User modifyUserdata(UserUpdateForm userUpdateForm);

    void deleteUser(Long userId);

    Page<User> findUsersWithPage(Pageable pageable);
}
