package org.programmers.springbootboardjpa.controller.dto;

import org.programmers.springbootboardjpa.domain.User;

public interface UserUpdateForm {
    User applyToUser(User user);

    Long id();
}
