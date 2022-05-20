package org.programmers.springbootboardjpa.web.dto.user;

import org.programmers.springbootboardjpa.domain.user.User;

public interface UserCreateForm {
    User convertToUser();
}