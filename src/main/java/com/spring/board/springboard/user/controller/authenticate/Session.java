package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.exception.SessionCreateException;

import java.util.Objects;

public record Session(String sessionId, String email) {

    public Session {
        if (sessionId.isEmpty() ||
                sessionId.isBlank() ||
                Objects.isNull(email) ||
                email.isBlank()
        ) {
            throw new SessionCreateException("사용자 정보를 전달받지 못해 세션을 만들 수 없습니다.");
        }
    }

    public static Session create(String sessionId, String email) {
        return new Session(sessionId, email);
    }
}
