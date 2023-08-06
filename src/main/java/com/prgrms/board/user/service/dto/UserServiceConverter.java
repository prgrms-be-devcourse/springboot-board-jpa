package com.prgrms.board.user.service.dto;

import com.prgrms.board.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceConverter {

    public User toUser(UserDetailedParam param) {
        return new User(
                param.name(),
                param.age(),
                param.hobby()
        );
    }

    public UserShortResult toUserShortResult(User savedUser) {
        return new UserShortResult(savedUser.getId());
    }
}
