package com.programmers.board.dto.service.login;

import com.programmers.board.dto.request.LoginRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginCommand {
    private final String name;

    public static LoginCommand from(LoginRequest request) {
        return new LoginCommand(request.getName());
    }

    public static LoginCommand of(String name) {
        return new LoginCommand(name);
    }
}
