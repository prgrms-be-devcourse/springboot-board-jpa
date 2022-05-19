package org.programmers.springbootboardjpa.web.user.dto.user;

import org.programmers.springbootboardjpa.domain.user.User;

public interface UserUpdateForm {
    User applyToUser(User user);

    Long id();
}
