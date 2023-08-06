package com.programmers.jpa_board.user.application;

import com.programmers.jpa_board.user.domain.User;

public interface UserProviderService {
    User getOne(Long id);
}
