package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.exception.AuthenticateException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStorage {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session getSession(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new AuthenticateException("인증되지 않은 사용자입니다. 사용자 인증이 필요합니다.");
        }
        return sessions.get(sessionId);
    }

    public void putSession(Session session) {
        sessions.put(session.sessionId(), session);
    }

    public Optional<Session> getSessionByEmail(String email) {
        return sessions.values()
                .stream()
                .filter(session -> Objects.equals(email, session.email()))
                .findAny();
    }
}
