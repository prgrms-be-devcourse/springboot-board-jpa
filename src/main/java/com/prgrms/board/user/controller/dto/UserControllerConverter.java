package com.prgrms.board.user.controller.dto;

import com.prgrms.board.user.service.dto.UserDetailedParam;
import com.prgrms.board.user.service.dto.UserShortResult;
import org.springframework.stereotype.Component;

@Component
public class UserControllerConverter {
    public UserDetailedParam toUserDetailedParam(UserDetailedRequest request) {
        return new UserDetailedParam(
                request.name(),
                request.age(),
                request.hobby()
        );
    }

    public UserShortResponse toUserResponse(UserShortResult result) {
        return new UserShortResponse(result.id());
    }
}
