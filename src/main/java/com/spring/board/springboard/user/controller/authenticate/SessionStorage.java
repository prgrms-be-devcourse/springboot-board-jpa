package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.exception.AuthenticateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStorage {

    private static final Logger logger = LoggerFactory.getLogger(SessionStorage.class);

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

    public void remove(Session session) {
        try {
            sessions.remove(
                    session.sessionId());
        } catch (NullPointerException nullPointerException) {
            logger.info(
                    MessageFormat.format("유효하지 않은 값으로 로그아웃을 요청함. sessionId: {0}, email: {1}",
                            session.sessionId(), session.email()),
                    nullPointerException);
        }

    }
}
