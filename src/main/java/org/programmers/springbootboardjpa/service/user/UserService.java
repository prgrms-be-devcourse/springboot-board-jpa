package org.programmers.springbootboardjpa.service.user;

import org.programmers.springbootboardjpa.domain.user.User;
import org.programmers.springbootboardjpa.web.dto.user.UserCreateForm;
import org.programmers.springbootboardjpa.web.dto.user.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Long registerUser(UserCreateForm userCreateForm);

    User findUserWith(Long userId);

    User modifyUserdata(UserUpdateForm userUpdateForm);

    void deleteUser(Long userId);

    Page<User> findUsersWithPage(Pageable pageable);
}
