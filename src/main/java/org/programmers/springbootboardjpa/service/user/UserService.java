package org.programmers.springbootboardjpa.service.user;

import org.programmers.springbootboardjpa.web.user.dto.user.UserCreateFormV1;
import org.programmers.springbootboardjpa.web.user.dto.user.UserUpdateForm;
import org.programmers.springbootboardjpa.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Long registerUser(UserCreateFormV1 userCreateForm);

    User findUserWith(Long userId);

    User modifyUserdata(UserUpdateForm userUpdateForm);

    void deleteUser(Long userId);

    Page<User> findUsersWithPage(Pageable pageable);
}
